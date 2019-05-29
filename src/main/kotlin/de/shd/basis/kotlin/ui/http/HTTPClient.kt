package de.shd.basis.kotlin.ui.http

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
     * Sendet den übergebenen [HTTPRequest] asynchron an die im Request enthaltene URL.
     *
     * Sobald der Server antwortet, wird die Methode [Promise.then] des zurückgegebenen [Promises][Promise] mit der erhaltenen [HTTPResponse]
     * aufgerufen.
     *
     * Falls keine Verbindung zum Server hergestellt werden kann, die Antwort des Servers zu lange dauert oder der Server einen Fehler meldet, wird
     * die Methode [Promise.catch] des zurückgegebenen [Promises][Promise] mit einer [SHDHTTPRequestException] oder [SHDTimeoutException] als Argument
     * aufgerufen.
     */
    fun send(request: HTTPRequest): Promise<HTTPResponse> {
        return Promise { resolve, reject ->
            val httpRequest = XMLHttpRequest()

            httpRequest.timeout = request.timeout
            httpRequest.addEventListener("load", { resolve(HTTPResponse(httpRequest)) })
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
}