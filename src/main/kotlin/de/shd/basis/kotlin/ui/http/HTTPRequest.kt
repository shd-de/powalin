package de.shd.basis.kotlin.ui.http

import de.shd.basis.kotlin.ui.http.HTTPRequest.Companion.buildFor
import de.shd.basis.kotlin.ui.http.auth.HTTPAuthorization
import de.shd.basis.kotlin.ui.media.MediaType
import de.shd.basis.kotlin.ui.serialization.generator.SHDGenerator
import de.shd.basis.kotlin.ui.time.TimeUnit
import kotlinx.serialization.KSerializer

/**
 * Repräsentiert einen konfigurierbaren HTTP-Request. Instanzen von dieser Klasse können ausschließlich via der Buillder-API [buildFor] erzeugt werden.
 *
 * Wenn die Standard-Konfigurationen dieser Klasse nicht manuell geändert werden, wird ein HTTP-Request wie folgt konfiguriert:
 * - **HTTP-Methode:** GET
 * - **Timeout:** 30 Sekunden
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class HTTPRequest private constructor(internal val url: String) {

    internal var method = HTTPMethod.GET                    // Standardmäßig nur einen lesenden HTTP-Request senden.
    internal var mediaType: MediaType? = null               // Standardmäßig legt ein Request nicht fest, was für Daten er erwartet oder versendet.
    internal var timeout = 0                                // Ist der Standardwert von Webbrowsern und bedeutet "kein Timeout". Wird aber in der init-Methode überschrieben.
    internal var body: HTTPBody<*>? = null                  // Standardmäßig werden keine Daten im Body des HTTP-Requests mitgesendet.
    internal var authorization: HTTPAuthorization? = null   // Standardmäßig muss ein HTTP-Request nicht authentifiziert oder authorisiert werden.

    /**
     * Timeout des HTTP-Requests standardmäßig auf 30 Sekunden setzen.
     */
    init {
        withTimeout(30, TimeUnit.SECONDS)
    }

    /**
     * Legt die übergebene [HTTPMethod] als HTTP-Methode dieses HTTP-Requests fest.
     */
    fun withMethod(method: HTTPMethod): HTTPRequest {
        this.method = method
        return this
    }

    /**
     * Legt fest, dass dieser HTTP-Request nach Überschreiten der spezifizierten Dauer abgebrochen werden soll, falls der Server vorher nicht
     * antwortet.
     */
    fun withTimeout(duration: Int, unit: TimeUnit): HTTPRequest {
        timeout = unit.toMillis(duration)
        return this
    }

    /**
     * Legt anhand der übergebenen Implementierung von [HTTPAuthorization] fest, wie sich dieser HTTP-Request gegen ein Backend authentifiziert bzw.
     * authorisiert.
     */
    fun withAuthorization(authorization: HTTPAuthorization): HTTPRequest {
        this.authorization = authorization
        return this
    }

    /**
     * Legt fest, was für Daten in einem textuellen Datenformat im Body dieses HTTP-Requests mitgesendet werden sollen. Damit dies möglich ist, muss
     * der [MediaType] angegeben werden, der dem Framework und dem Server mitteilt, in was für einem Datenformat die Daten gesendet werden (sollen).
     *
     * Da die mitzusendenden Daten in Form eines beliebigen Objekts übergeben werden können, muss darüber hinaus noch ein [Serializer][KSerializer]
     * übergeben werden, der in der Lage ist, das übergebene Objekt im Zusammenspiel mit einer beliebigen Implementierung von [SHDGenerator] in das
     * gewünschte, textuelle Datenformat zu konvertieren.
     */
    fun <DATA> withBody(bodyMediaType: MediaType, bodySerializer: KSerializer<DATA>, data: DATA): HTTPRequest {
        this.body = HTTPBody(data, bodySerializer)
        this.mediaType = bodyMediaType

        return this
    }

    /**
     * Statische Builder-API.
     */
    companion object {

        /**
         * Erzeugt eine Instanz von [HTTPRequest] für die übergebene URL. Es muss ein [HTTPRequest] pro Ziel-URL erzeugt werden. Denn die URL dieses
         * Requests kann hinterher nicht mehr geändert werden.
         */
        fun buildFor(url: String): HTTPRequest {
            return HTTPRequest(url)
        }
    }
}