package de.shd.basis.kotlin.ui.http

import de.shd.basis.kotlin.ui.media.MediaType
import de.shd.basis.kotlin.ui.serialization.converter.SHDJSONConverter
import de.shd.basis.kotlin.ui.serialization.generator.SHDGenerator
import de.shd.basis.kotlin.ui.serialization.parser.SHDParser
import de.shd.basis.kotlin.ui.util.constant.SEMICOLON
import de.shd.basis.kotlin.ui.util.exception.SHDHTTPRequestException
import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
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
     * Der Standard-Konvertier für den Media Type [APPLICATION_JSON][MediaType.APPLICATION_JSON].
     */
    private val defaultJSONConverter = SHDJSONConverter()

    /**
     * Enthält die Mappings von [Media Types][MediaType] zu Implementierungen von [SHDParser], die entsprechende Datenstrukturen parsen und
     * konvertieren können.
     */
    private val parserMap: Map<String, SHDParser> = mutableMapOf(
            MediaType.APPLICATION_JSON.type to defaultJSONConverter
    )

    /**
     * Enthält die Mappings von [Media Types][MediaType] zu Implementierungen von [SHDGenerator], die Objekte in entsprechende Datenstrukturen
     * konvertieren können.
     */
    private val generatorMap: Map<String, SHDGenerator> = mutableMapOf(
            MediaType.APPLICATION_JSON.type to defaultJSONConverter
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
            val httpRequest = openXMLHttpRequest(request)

            httpRequest.addEventListener("load", { resolve(HTTPResponse(httpRequest, findParserForBody(httpRequest))) })
            httpRequest.addEventListener("error", { reject(createHTTPRequestException(httpRequest)) })
            httpRequest.addEventListener("timeout", { reject(SHDTimeoutException("HTTP-Request hat zu lange gedauert")) })
            httpRequest.send(createXMLHTTPRequestBody(request))
        }
    }

    /**
     * Erzeugt, öffnet und konfiguriert einen [XMLHttpRequest] für den übergebenen [HTTPRequest]. Wie genau der [XMLHttpRequest] konfiguriert wird,
     * leitet diese Methode dabei vom übergebenen [HTTPRequest] ab. Dazu gehört u.a. die Entscheidung, was für HTTP-Header mitgesendet werden sollen.
     */
    private fun openXMLHttpRequest(request: HTTPRequest): XMLHttpRequest {
        val mediaType = request.mediaType
        val httpRequest = XMLHttpRequest()

        httpRequest.open(request.method.name, request.url) // Der XMLHttpRequest muss geöffnet werden, bevor HTTP-Header gesetzt werden können.
        httpRequest.timeout = request.timeout // Der XMLHttpRequest soll immer eine Maximaldauer haben und dann bei Überschreitung abbrechen.

        // Wenn ein Media Type im HTTPRequest festgelegt wurde, wird davon ausgegangen, dass die ggf. zu versendende und zu empfangene Datenstruktur
        // das gleiche Datenformat haben.
        if (mediaType != null) {
            httpRequest.setRequestHeader("Content-Type", mediaType.type)
            httpRequest.setRequestHeader("Accept", mediaType.type)
        }

        return httpRequest
    }

    /**
     * Erzeugt einen serialisierten Body für einen [XMLHttpRequest] auf Basis eines möglicherweise im übergebenen [HTTPRequest] vorhandenen
     * [HTTPBodys][HTTPBody].
     *
     * Da im [HTTPBody] ein beliebiges Objekt enthalten sein kann, wird versucht, einen [SHDGenerator] für den im [HTTPRequest] angegebenen [MediaType]
     * zu ermitteln. Falls kein solcher Media Type festgelegt wurde oder kein passender Generator ermittelt werden konnte, wird eine [SHDRuntimeException]
     * geworfen.
     *
     * Schließlich wird der erzeugte Body zurückgegeben, falls ein [HTTPBody] im [HTTPRequest] vorhanden ist. Andernfalls [undefined].
     */
    private fun createXMLHTTPRequestBody(request: HTTPRequest): String? {
        val requestBody = request.body
        var serializedBody: String? = undefined

        if (requestBody != null) {
            val mediaType = request.mediaType ?: throw SHDRuntimeException("Ein Media Type muss fuer den Request-Body angegeben werden")
            val generator = generatorMap[mediaType.type] ?: throw SHDRuntimeException("Ein Generator muss fuer den Request-Body festgelegt werden")

            serializedBody = requestBody.stringify(generator)
        }

        return serializedBody
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
            val mediaType = contentType.split(SEMICOLON).firstOrNull() // Encoding ignorieren (z.B. von "application/json;charset=UTF-8")

            if (mediaType != null) {
                foundParser = parserMap[mediaType]
            }
        }

        return foundParser
    }
}