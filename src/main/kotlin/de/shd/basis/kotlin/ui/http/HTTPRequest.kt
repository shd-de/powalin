package de.shd.basis.kotlin.ui.http

import de.shd.basis.kotlin.ui.http.HTTPRequest.Companion.buildFor
import de.shd.basis.kotlin.ui.time.TimeUnit

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

    internal var method = HTTPMethod.GET // Standardmäßig nur einen lesenden HTTP-Request versenden.
    internal var timeout = 0             // Ist der Standardwert von Webbrowsern und bedeutet "kein Timeout". Wird aber in der init-Methode überschrieben.

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