@file:Suppress("unused")

package de.shd.basis.kotlin.ui.app

import de.shd.basis.kotlin.ui.checker.ConnectivityChecker
import de.shd.basis.kotlin.ui.component.menu.app.AppMenuController
import de.shd.basis.kotlin.ui.http.HTTPMethod
import de.shd.basis.kotlin.ui.i18n.I18n
import de.shd.basis.kotlin.ui.i18n.I18nMessageProvider
import de.shd.basis.kotlin.ui.mvc.controller.MVCController
import de.shd.basis.kotlin.ui.mvc.controller.MVCControllerFactory
import de.shd.basis.kotlin.ui.state.AppStateManager
import de.shd.basis.kotlin.ui.util.function.appendChild
import de.shd.basis.kotlin.ui.util.function.appendScript
import de.shd.basis.kotlin.ui.util.function.appendScripts
import de.shd.basis.kotlin.ui.util.function.appendStylesheet
import de.shd.basis.kotlin.ui.util.function.appendStylesheets
import de.shd.basis.kotlin.ui.util.function.removeAllChildren
import de.shd.basis.kotlin.ui.worker.ServiceWorkerRegistry
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Promise
import kotlin.reflect.KClass

/**
 * Diese Klasse ist der zentrale Einstiegspunkt des auf Kotlin und dem MVC-Muster basierenden Basis-Frameworks für offlinefähige Single Page
 * Applications (SPA).
 *
 * Im Regelfall wird sie in der `main`-Methode einer Anwendung instanziiert. An den Standard-Konstruktor muss der Anwendungstitel der Anwendung
 * übergeben werden, der im Fenster bzw. Tab des Webbrowsers angezeigt wird.
 *
 * Anschließend kann über die API dieser Klasse das Framework konfiguriert und so u.a. bei Bedarf zusätzliche JavaScript und CSS-Dateien zur
 * zugrundeliegenden `index.html` hinzugefügt werden. Auch kann über die API festgelegt werden, welche MVC-Komponente standardmäßig bzw. initial
 * angezeigt werden soll. Wenn keine initial anzuzeigende MVC-Komponente von einer Anwendung explizit festgelegt wird, wird standardmäßig das zentrale,
 * konfigurierbare Anwendungsmenü des Frameworks angezeigt (siehe [AppMenuController]).
 *
 * Sobald alle anwendungsspezifischen Standard-Konfigurationen durchgeführt wurden, kann das Framework (und damit die Anwendung) via [run] gestartet
 * werden. Diese Methode kümmert sich anschließend um das vollständige initialisieren des Frameworks und des (initialen) DOMS. Nach Aufruf dieser
 * Methode ist es nicht mehr möglich, weitere Konfigurationen über die API dieser Klasse durchzuführen.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SHDApp(private val appTitle: String) {

    private var frameworkRootElement: Element? = null

    private val customScripts = mutableListOf<String>()
    private val customStylesheets = mutableListOf<String>()

    /**
     * Legt anhand der übergebenen [Controller-Klasse][MVCController] fest, welche (anwendungsspezifische) Ansicht standardmäßig bzw. initial
     * angezeigt werden soll.
     *
     * Das Framework übernimmt die Erzeugung einer "Zustands-Instanz" für den spezifizierten Controller und hälts sie intern vor. Daher kann dieser
     * Standard-Zustand (bzw. diese Standard-Ansicht) jederzeit wiederhergestellt werden, falls die Anwendung via [AppStateManager.openState]
     * oder [AppStateManager.openAsNewState] zwischenzeitlich die Ansicht gewechselt hat.
     */
    fun <CONTROLLER : MVCController<*>> withDefaultState(controllerClass: KClass<CONTROLLER>): SHDApp {
        return withDefaultState(MVCControllerFactory.create(controllerClass))
    }

    /**
     * Legt anhand des übergebenen [Controllers][MVCController] fest, welche (anwendungsspezifische) Ansicht standardmäßig bzw. initial angezeigt
     * werden soll.
     *
     * Das Framework hält anschließend die übergebene "Zustands-Instanz" intern vor. Daher kann dieser Standard-Zustand (bzw. diese Standard-Ansicht)
     * jederzeit wiederhergestellt werden, falls die Anwendung via [AppStateManager.openState] oder [AppStateManager.openAsNewState] zwischenzeitlich
     * die Ansicht gewechselt hat.
     */
    fun withDefaultState(controller: MVCController<*>): SHDApp {
        AppStateManager.defaultStateController = controller
        return this
    }

    /**
     * Importiert die spezifizierten JavaScript-Dateien, indem via [appendScript] ein zusätzliches `script`-Element pro übergebener Datei-URL zum DOM
     * hinzugefügt wird.
     */
    fun withScripts(vararg scriptURLs: String): SHDApp {
        customScripts.addAll(scriptURLs)
        return this
    }

    /**
     * Importiert die spezifizierten CSS-Dateien, indem via [appendStylesheet] ein zusätzliches `link`-Element pro übergebener Datei-URL zum DOM
     * hinzugefügt wird.
     */
    fun withStylesheets(vararg stylesheetURLs: String): SHDApp {
        customStylesheets.addAll(stylesheetURLs)
        return this
    }

    /**
     * Legt die Implementierung von [I18nMessageProvider] fest, die von [I18n.getText] zum Auflösen von Übersetzung auf Basis von I18n-Schlüsseln
     * verwendet werden soll.
     */
    fun withI18nMessageProvider(messageProvider: I18nMessageProvider): SHDApp {
        I18n.messageProvider = messageProvider
        return this
    }

    /**
     * Legt die aktuelle Sprache der Anwendung anhand eines Sprach-Codes fest.
     *
     * Das Format, das Sprach-Codes haben müssen, ist durch [BCP 47](https://tools.ietf.org/rfc/bcp/bcp47.txt) festgelegt. Zum Beispiel sind die
     * folgenden Sprach-Code-Formate valide:
     * - en
     * - en-US
     * - de
     * - de-DE
     *
     * @see I18n.currentLanguage
     */
    fun withLanguage(language: String): SHDApp {
        I18n.currentLanguage = language
        return this
    }

    /**
     * Ermöglicht die Konfiguration des zentralen Anwendungsmenüs über die übergebene Funktion, indem dessen [Controller][AppMenuController] als
     * Receiver an diese Funktion übergeben wird.
     */
    fun configureAppMenu(configurator: AppMenuController.() -> Unit): SHDApp {
        AppStateManager.configureAppMenuState(configurator)
        return this
    }

    /**
     * Registriert den [ServiceWorker](https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API), auf den die übergebene URL zeigt, mit dem
     * Standard-Scope via [ServiceWorkerRegistry.register]. Der Standard-Scope entspricht dem Pfad, in dem der `ServiceWorker` liegt, inklusive allen
     * Unterpfaden.
     */
    fun registerServiceWorker(scriptURL: String): SHDApp {
        ServiceWorkerRegistry.register(scriptURL)
        return this
    }

    /**
     * Registriert den [ServiceWorker](https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API), auf den die übergebene URL zeigt, mit dem
     * übergebenen Scope via [ServiceWorkerRegistry.register]. Der übergebene Scope muss einem Pfad entsprechen, in dem der `ServiceWorker` liegt,
     * oder einem Unterpfad davon.
     */
    fun registerServiceWorker(scriptURL: String, scope: String): SHDApp {
        ServiceWorkerRegistry.register(scriptURL, scope)
        return this
    }

    /**
     * Aktiviert eine zusätzliche, regelmäßige Prüfung der Netzwerkverbindung via [ConnectivityChecker.enablePeriodicConnectionCheck], die
     * [HEAD-Requests][HTTPMethod.HEAD] im angegebenen Intervall an die spezifizierte URL sendet und am Erfolg jeder einzelnen Anfrage festmacht, ob
     * eine Netzwerkverbindung besteht oder nicht. Eine solche Anfrage wird auch umgehend einmalig im Rahmen der Ausführung dieser Methode
     * durchgeführt (sprich ohne Einbezug des angegebenen Intervalls).
     *
     * @see ConnectivityChecker
     */
    fun enablePeriodicConnectionCheck(targetURL: String, intervalDelay: Int, connectionTimeout: Int = ConnectivityChecker.DEFAULT_CONNECTION_TIMEOUT): SHDApp {
        ConnectivityChecker.enablePeriodicConnectionCheck(targetURL, intervalDelay, connectionTimeout)
        return this
    }

    /**
     * Aktiviert eine zusätzliche, regelmäßige Prüfung der Netzwerkverbindung via [ConnectivityChecker.enablePeriodicConnectionCheck], die
     * [HEAD-Requests][HTTPMethod.HEAD] im angegebenen Intervall an die URL sendet, die von der übergebenen Funktion in einem [Promise] zurückgegeben
     * wird. Daraufhin wird am Erfolg jeder einzelnen Anfrage festgemacht, ob eine Netzwerkverbindung besteht oder nicht. Eine solche Anfrage wird
     * auch umgehend einmalig im Rahmen der Ausführung dieser Methode durchgeführt (sprich ohne Einbezug des angegebenen Intervalls).
     *
     * @see ConnectivityChecker
     */
    fun enablePeriodicConnectionCheck(targetURLProvider: () -> Promise<String>, intervalDelay: Int, connectionTimeout: Int = ConnectivityChecker.DEFAULT_CONNECTION_TIMEOUT): SHDApp {
        ConnectivityChecker.enablePeriodicConnectionCheck(targetURLProvider, intervalDelay, connectionTimeout)
        return this
    }

    /**
     * Startet die Anwendung, indem das Basis-Framework initialisiert wird, welches widerum die eigentliche Anwendung initialisiert.
     */
    fun run() {
        // Anwendungsspezifische Ressourcen zum DOM hinzufügen.
        appendScripts(customScripts)
        appendStylesheets(customStylesheets)

        // Den Titel des aktuellen HTML-Dokuments festlegen. Dieser wird dann im Fenster bzw. Tab des Webbrowsers angezeigt und fungiert damit als
        // "Anwendungstitel".
        document.title = appTitle

        // Die grundlegende DOM-Struktur für die Anwendung initialisieren, sobald der Webbrowser die Datei "index.html" vollständig geladen und
        // geparst hat. Erst danach kann via JavaScript zuverlässig auf die (bereits vorhandenen) Elemente des DOMs zugegriffen werden.
        window.addEventListener("DOMContentLoaded", { initializeAppDOM() })

        // Auf Änderungen des Anwendungszustands lauschen, damit die DOM-Struktur entsprechend aktualisiert werden kann.
        AppStateManager.addStateChangeListener(this::replaceAppSubTree)
    }

    /**
     * Initialisiert die grundlegende DOM-Struktur der Anwendung und überschreibt bei Bedarf auch Attribute-Werte von bereits vorhandenen Elementen.
     */
    private fun initializeAppDOM() {
        // Das Framework-interne Wurzelelement der Anwendung ermitteln und den DOM-Subtree der (initialen) Standard-Ansicht als Kindelement hinzufügen.
        frameworkRootElement = document.querySelector(".shd-app")
        frameworkRootElement?.appendChild(AppStateManager.defaultStateController.view)

        // Die (deklarative) Sprache des Dokuments aktualisieren, damit sie auch zur tatsächlich verwendeten bzw. angezeigten Sprache passt.
        (document.documentElement as HTMLElement).lang = I18n.currentLanguage
    }

    /**
     * Den Wurzelknoten der aktuellen Ansicht aus dem DOM-Baum des Frameworks aushängen und den Wurzelknoten der View, die vom übergebenen Controller
     * verwaltet wird, stattdessen in den DOM-Baum des Frameworks einhängen.
     */
    private fun replaceAppSubTree(newStateCtrl: MVCController<*>) {
        val rootElement = frameworkRootElement

        if (rootElement != null) {
            rootElement.removeAllChildren()
            rootElement.appendChild(newStateCtrl.view)
        }
    }
}