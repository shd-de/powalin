package de.shd.basis.kotlin.ui.http

import de.shd.basis.kotlin.ui.parser.SHDJSONParser
import de.shd.basis.kotlin.ui.parser.SHDParser
import de.shd.basis.kotlin.ui.util.exception.SHDHTTPRequestException
import de.shd.basis.kotlin.ui.util.exception.SHDTimeoutException
import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise

/**
 * Ein einfacher, konfigurierbarer Client zum Senden von HTTP-Requests an Server. Intern kapselt er die native Webbrowser-API zum Senden solcher
 * Requests (siehe [XMLHttpRequest](https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest))
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class HTTPClient {

    /**
     * Enthält die Mappings von MediaTypes zu Implementierungen von [SHDParser], die entsprechende Datenstrukturen parsen und konvertieren können.
     */
    private val parserMap: Map<String, SHDParser> = mutableMapOf(
            "application/json" to SHDJSONParser()
    )

    /**
     * Sendet den übergebenen [HTTPRequest] asynchron an die im Request-Objekt enthaltene URL.
     *
     * Sobald der Server antwortet, wird die Methode [Promise.then] des zurückgegebenen [Promises][Promise] mit der vom Server erhaltenen [HTTPResponse]
     * aufgerufen. Über dieses Response-Objekt können anschließend die Informationen ausgelesen werden, die der Server zurückgegeben hat. Des Weiteren
     * kann über dieses Response-Objekt der Nachrichtenrumpf (`Body`) der Antwort als deserialisiertes Objekt ausgelesen werden.
     *
     * Falls keine Verbindung zum Server hergestellt werden kann, die Antwort des Servers zu lange dauert oder der Server einen Fehler meldet, wird
     * die Methode [Promise.catch] des zurückgegebenen [Promises][Promise] mit einer [SHDHTTPRequestException] oder [SHDTimeoutException] als Argument
     * aufgerufen.
     */
    fun send(request: HTTPRequest): Promise<HTTPResponse> {
        return Promise { resolve, reject ->
            val httpRequest = XMLHttpRequest()

            httpRequest.timeout = request.timeout
            httpRequest.addEventListener("load", { resolve(HTTPResponse(httpRequest, findParserForBody(httpRequest))) })
            httpRequest.addEventListener("error", { reject(createHTTPRequestException(httpRequest)) })
            httpRequest.addEventListener("timeout", { reject(SHDTimeoutException("HTTP-Request hat zu lange gedauert")) })
            httpRequest.open(request.method.name, request.url)
            httpRequest.send()
        }
    }

    /**
     * Erzeugt eine Instanz von [SHDHTTPRequestException], die den HTTP-Status-Code und den Inhalt der übergebenen HTTP-Response enthält.
     */
    private fun createHTTPRequestException(httpRequest: XMLHttpRequest): SHDHTTPRequestException {
        return SHDHTTPRequestException(httpRequest.status, httpRequest.responseText, "HTTP-Request fehlgeschlagen")
    }

    /**
     * Versucht, anhand des Werts des HTTP-Headers `content-type`, der im übergebenen [XMLHttpRequest] enthalten ist und den ein Server als Teil
     * seiner Response mitgesendet hat, eine passende Implementierung von [SHDParser] zu ermitteln, die in der Lage ist, den Body der Response zu
     * parsen und in ein Datenobjekt zu konvertieren.
     *
     * Falls ein Server den o.g. HTTP-Header nicht mitgesendet hat oder keine passende Implementierung von [SHDParser] ermittelt werden konnte, wird
     * `null` zurückgegeben.
     */
    private fun findParserForBody(httpRequest: XMLHttpRequest): SHDParser? {
        val contentType = httpRequest.getResponseHeader(HTTPHeaders.CONTENT_TYPE)
        var foundParser: SHDParser? = null

        if (contentType != null && !contentType.isBlank()) {
            val mediaType = contentType.split(';').firstOrNull() // Encoding ignorieren (z.B. von "application/json;charset=UTF-8")

            if (mediaType != null) {
                foundParser = parserMap[mediaType]
            }
        }

        return foundParser
    }
}