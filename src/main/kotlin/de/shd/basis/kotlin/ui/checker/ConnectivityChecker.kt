package de.shd.basis.kotlin.ui.checker

import de.shd.basis.kotlin.ui.checker.ConnectivityChecker.enablePeriodicConnectionCheck
import de.shd.basis.kotlin.ui.http.HTTPClient
import de.shd.basis.kotlin.ui.http.HTTPMethod
import de.shd.basis.kotlin.ui.http.HTTPRequest
import de.shd.basis.kotlin.ui.time.TimeUnit
import de.shd.basis.kotlin.ui.util.promise.DefaultRepeatablePromise
import de.shd.basis.kotlin.ui.util.promise.ResolvablePromise
import org.w3c.dom.Navigator
import org.w3c.dom.WindowOrWorkerGlobalScope
import kotlin.browser.window
import kotlin.js.Promise

/**
 * Der zentrale Verbindungsprüfer, über den auf Änderungen der Netzwerkverbindung reagiert werden kann. D.h. er übernimmt u.a. die (konstante) Prüfung
 * der Netzwerkverbindung und informiert registrierte Listener, sobald sich diese ändert.
 *
 * Standardmäßig lauscht dieser Verbindungsprüfer nur auf die Events `online` und `offline`, die vom Webbrowser versendet werden, wenn sich die
 * Netzerkverbindung ändert. Ersteres wird versendet, wenn eine Verbindung (wieder) besteht und letzteres, wenn keine (mehr) besteht. Diese Events
 * sind allerdings nicht zuverlässig, weil der Webbrowser entscheidet, was konkret "online" bedeutet. Google Chrome und Safari bspw. betrachten
 * bereits eine bestehende LAN-Verbindung als "online", was aber nicht zwingend bedeutet, dass das Internet oder das Backend der Anwendung erreichbar
 * sind.
 *
 * Daher stellt dieser Verbindungsprüfer einen zweiten, optional aktivierbaren Mechanismus zur Verfügung, über den er prüfen kann, ob eine
 * Netzwerkverbindung besteht. Dieser Mechanismus kann aktiviert werden, indem die Methode [enablePeriodicConnectionCheck] mit einer Ziel-URL
 * aufgerufen wird, die dieser Prüfer mit einem [HEAD-Request][HTTPMethod.HEAD] aufrufen kann. Anschließend ruft er diesen Endpunkt regelmäßig auf und
 * macht am Erfolg jeder einzelnen Anfrage fest, ob eine Netzwerkverbindung besteht oder nicht. Diese Prüfungen führt er in einem konfigurierbaren
 * Intervall endlos durch und informiert registrierte Listener entsprechend bei Bedarf über das Ergebnis.
 *
 * Zwar wird mit dem zweiten Ansatz auch nicht (zwingend) gewährleistet, dass eine Internetverbindung besteht. Aber zumindest gewährleistet er, dass
 * der zu prüfende Endpunkt erreichbar ist. Im Regelfall soll eine URL des Backends der Anwendung an die Methode [enablePeriodicConnectionCheck]
 * übergeben werden, die für Pings gedacht ist und dementsprechend möglichst geringe und sparsame Laufzeiten hat. Generell ist aber darauf zu achten,
 * dass das Intervall so gewählt wird, dass die Pings nicht das Backend (zu sehr) unter Last setzen.
 *
 * @see Navigator.onLine
 * @author Florian Steitz (fst)
 */
object ConnectivityChecker {

    internal const val DEFAULT_CONNECTION_TIMEOUT = 5

    private val onOnlineListeners = mutableListOf<DefaultRepeatablePromise<Nothing?>>()
    private val onOfflineListeners = mutableListOf<DefaultRepeatablePromise<Nothing?>>()
    private val httpClient = HTTPClient()

    /**
     * Gibt an, ob laut diesem Verbindungsprüfer eine Netzwerkverbindung besteht oder nicht. Initial wird dabei der Online-Status übernommen, den der
     * Webbrowser festlegt.
     */
    private var isConnectionAvailable = window.navigator.onLine

    /**
     * Enthält optional die ID eines Timers, der [WindowOrWorkerGlobalScope.setInterval] zugrunde liegt und die von der Methode zurückgegeben wird.
     * Eine solche ID wird benötigt, um ein aktives Intervall abbrechen zu können und ist daher nur gesetzt, wenn ein Intervall aktiv ist.
     */
    private var connectionCheckIntervalID: Int? = null

    /**
     * Registriert Listener für die Events `online` und `offline`, die vom Webbrowser versendet werden, wenn sich die Netzerkverbindung ändert.
     */
    init {
        window.addEventListener("online", { handleConnectedState(isConnectionAvailable) })
        window.addEventListener("offline", { handleDisconnectedState(isConnectionAvailable) })
    }

    /**
     * Registriert ein neues [ResolvablePromise] bei diesem Verbindungsprüfer und gibt es zurück. Anschließend kann über die Methode [ResolvablePromise.then]
     * ein anwendungsspezifischer Listener registriert werden, der immer dann aufgerufen wird, wenn eine Netzwerkverbindung als (wieder)hergestellt
     * gilt. Allerdings nur einmalig pro Änderung der Netzwerkverbindung. Darüber hinaus wird der so registrierte Listener umgehend aufgerufen, falls
     * aktuell bereits eine Netzwerkverbindung besteht.
     */
    fun whenOnline(): ResolvablePromise<Nothing?> {
        return addPromise(onOnlineListeners, isConnectionAvailable)
    }

    /**
     * Registriert ein neues [ResolvablePromise] bei diesem Verbindungsprüfer und gibt es zurück. Anschließend kann über die Methode [ResolvablePromise.then]
     * ein anwendungsspezifischer Listener registriert werden, der immer dann aufgerufen wird, wenn keine Netzwerkverbindung (mehr) besteht.
     * Allerdings nur einmalig pro Änderung der Netzwerkverbindung. Darüber hinaus wird der so registrierte Listener umgehend aufgerufen, falls
     * derzeit keine Netzwerkverbindung besteht.
     */
    fun whenOffline(): ResolvablePromise<Nothing?> {
        return addPromise(onOfflineListeners, !isConnectionAvailable)
    }

    /**
     * Aktiviert eine zusätzliche, regelmäßige Prüfung der Netzwerkverbindung, die [HEAD-Requests][HTTPMethod.HEAD] im angegebenen Intervall in
     * Millisekunden an die spezifizierte URL sendet und am Erfolg jeder einzelnen Anfrage festmacht, ob eine Netzwerkverbindung besteht oder nicht.
     * Eine solche Anfrage wird auch umgehend einmalig im Rahmen der Ausführung dieser Methode durchgeführt (sprich ohne Einbezug des angegebenen
     * Intervalls).
     *
     * Im Regelfall soll an diese Methode eine URL des Backends der Anwendung übergeben werden, die für Pings gedacht ist und dementsprechend
     * möglichst geringe und sparsame Laufzeiten hat. Zum einen, um das Backend zu schonen und zum anderen, weil bei jeder Anfrage standardmäßig ein
     * vergleichsweise geringer Connection-Timeout (5 Sekunden) konfiguriert ist, um möglichst schnell feststellen zu können, ob eine
     * Netzwerkverbindung besteht. Dieser Standardwert kann allerdings über den optionalen Parameter `connectionTimeout` übersteuert werden.
     * Unabhängig davon ist aber darauf zu achten, dass das Intervall so gewählt wird, dass die Pings nicht das Backend (zu sehr) unter Last setzen.
     *
     * **Achtung:**
     *
     * Die hierüber aktivierte Prüfung erachtet jede fehlgeschlagene Anfrage als fehlende Netzwerkverbindung. Selbst wenn eine Anfrage wegen eines
     * internen Fehlers des Backends der Anwendung fehlschlägt. Daher soll die URL eines Endpunkts angegeben werden, der möglichst nicht fehlschlagen
     * kann (bspw. indem er immer nur den HTTP-Status 200 (OK) zurückgibt).
     *
     * Dies wurde so implementiert, weil das Backend einer Anwendung auch hängen bleiben kann und dadurch auch keine Kommunikation mit dem Backend
     * mehr möglich sein kann.
     */
    fun enablePeriodicConnectionCheck(targetURL: String, intervalDelay: Int, connectionTimeout: Int = DEFAULT_CONNECTION_TIMEOUT): ConnectivityChecker {
        val targetURLProvider: () -> Promise<String> = { Promise { resolve, _ -> resolve(targetURL) } }
        return enablePeriodicConnectionCheck(targetURLProvider, intervalDelay, connectionTimeout)
    }

    /**
     * Aktiviert eine zusätzliche, regelmäßige Prüfung der Netzwerkverbindung, die [HEAD-Requests][HTTPMethod.HEAD] im angegebenen Intervall in
     * Millisekunden an die URL sendet, die von der übergebenen Funktion in einem [Promise] zurückgegeben wird. Daraufhin wird am Erfolg jeder
     * einzelnen Anfrage festgemacht, ob eine Netzwerkverbindung besteht oder nicht. Eine solche Anfrage wird auch umgehend einmalig im Rahmen der
     * Ausführung dieser Methode durchgeführt (sprich ohne Einbezug des angegebenen Intervalls).
     *
     * Im Regelfall soll von der übergebenen Funktion eine URL des Backends der Anwendung zurückgegeben werden, die für Pings gedacht ist und
     * dementsprechend möglichst geringe und sparsame Laufzeiten hat. Zum einen, um das Backend zu schonen und zum anderen, weil bei jeder Anfrage
     * standardmäßig ein vergleichsweise geringer Connection-Timeout (5 Sekunden) konfiguriert ist, um möglichst schnell feststellen zu können, ob
     * eine Netzwerkverbindung besteht. Dieser Standardwert kann allerdings über den optionalen Parameter `connectionTimeout` übersteuert werden.
     * Unabhängig davon ist aber darauf zu achten, dass das Intervall so gewählt wird, dass die Pings nicht das Backend (zu sehr) unter Last setzen.
     *
     * Auch gilt zu beachten, dass diese Methode einen Listener über die Methode [Promise.catch] des von der übergebenen Funktion zurückgegebenen
     * [Promises][Promise] registriert, der im Falle eines Aufrufs die Netzwerkverbindung als nicht hergestellt erachtet.
     *
     * **Achtung:**
     *
     * Die hierüber aktivierte Prüfung erachtet jede fehlgeschlagene Anfrage als fehlende Netzwerkverbindung. Selbst wenn eine Anfrage wegen eines
     * internen Fehlers des Backends der Anwendung fehlschlägt. Daher soll die URL eines Endpunkts von der übergebenen Funktion zurückgegeben werden,
     * der möglichst nicht fehlschlagen kann (bspw. indem er immer nur den HTTP-Status 200 (OK) zurückgibt).
     *
     * Dies wurde so implementiert, weil das Backend einer Anwendung auch hängen bleiben kann und dadurch auch keine Kommunikation mit dem Backend
     * mehr möglich sein kann.
     */
    fun enablePeriodicConnectionCheck(targetURLProvider: () -> Promise<String>, intervalDelay: Int, connectionTimeout: Int = DEFAULT_CONNECTION_TIMEOUT): ConnectivityChecker {
        val intervalID = connectionCheckIntervalID

        // Falls bereits eine ID eines Timers bekannt ist, heißt das, es ist bereits ein Intervall aktiv. Daher muss das alte Intervall zuvor
        // verworfen werden, um Memory Leaks zu vermeiden.
        if (intervalID != null) {
            window.clearInterval(intervalID)
            connectionCheckIntervalID = null // Sicherheitshalber zurücksetzen.
        }

        // Anschließend wird direkt versucht, einen HEAD-Request an die Ziel-URL zu senden, um zu prüfen, ob aktuell eine Netzwerkverbindung besteht.
        pingURL(targetURLProvider, connectionTimeout)

        // Abschließend wird das Intervall gestartet, dass die obige Prüfung im angegebenen Intervall endlos durchführt.
        connectionCheckIntervalID = window.setInterval({ pingURL(targetURLProvider, connectionTimeout) }, intervalDelay)

        return this
    }

    /**
     * TODO
     */
    private fun addPromise(listeners: MutableCollection<DefaultRepeatablePromise<Nothing?>>, invokeImmediately: Boolean): ResolvablePromise<Nothing?> {
        val promise = DefaultRepeatablePromise<Nothing?> { invokeThen, _ -> if (invokeImmediately) invokeThen(null) }
        listeners.add(promise)

        return promise
    }

    /**
     * TODO Doc
     */
    private fun pingURL(targetURLProvider: () -> Promise<String>, connectionTimeout: Int) {
        val wasConnectionAvailable = isConnectionAvailable // TODO Doc
        val successHandler: (Any) -> Unit = { handleConnectedState(wasConnectionAvailable) }
        val errorHandler: (Throwable) -> Unit = { error -> handleConnectionError(error, wasConnectionAvailable) }

        targetURLProvider.invoke()
                .then { pingURL(it, connectionTimeout, successHandler, errorHandler) }
                .catch(errorHandler)
    }

    /**
     * TODO Doc
     */
    private fun pingURL(url: String, connectionTimeout: Int, successHandler: (Any) -> Unit, errorHandler: (Throwable) -> Unit) {
        httpClient.send(HTTPRequest.buildFor(url).withMethod(HTTPMethod.HEAD).withTimeout(connectionTimeout, TimeUnit.SECONDS))
                .then(successHandler)
                .catch(errorHandler)
    }

    /**
     * TODO Doc
     */
    private fun handleConnectionError(error: Throwable, wasConnectionAvailable: Boolean) {
        console.info("Verbindungspruefung ist fehlgeschlagen: ${error.message}") // TODO console.debug(...) verwenden, sobald möglich.
        handleDisconnectedState(wasConnectionAvailable)
    }

    /**
     * TODO Doc
     */
    private fun handleConnectedState(wasConnectionAvailable: Boolean) {
        if (!wasConnectionAvailable) {
            isConnectionAvailable = true
            onOnlineListeners.forEach(this::invokeThen)
        }
    }

    /**
     * TODO Doc
     */
    private fun handleDisconnectedState(wasConnectionAvailable: Boolean) {
        if (wasConnectionAvailable) {
            isConnectionAvailable = false
            onOfflineListeners.forEach(this::invokeThen)
        }
    }

    /**
     * TODO Doc
     */
    private fun invokeThen(promise: DefaultRepeatablePromise<Nothing?>) {
        promise.invokeThen(null)
    }
}