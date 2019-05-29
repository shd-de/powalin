package de.shd.basis.kotlin.ui.http

import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType

/**
 * Repräsentiert eine HTTP-Response. Instanzen von dieser Klasse werden ausschließlich durch den [HTTPClient] erzeugt.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class HTTPResponse internal constructor(private val httpRequest: XMLHttpRequest) {

    /**
     * Gibt den Wert des spezifizierten HTTP-Headers dieser HTTP-Response zurück, sofern vorhanden. Andernfalls `null`.
     */
    fun getHeaderValue(headerName: String): String? {
        return httpRequest.getResponseHeader(headerName)
    }

    /**
     * Gibt den HTTP-Status-Code dieser HTTP-Response zurück.
     */
    fun getStatusCode(): Short {
        return httpRequest.status
    }

    /**
     * Gibt den [XMLHttpRequestResponseType] des Inhalts dieser HTTP-Response zurück, den der Webbrowser ermittelt hat. Die Werte dieses Typ-Enums
     * sind auf Datenformate beschränkt, die der Webbrowser standardmäßig verarbeiten bzw. parsen kann.
     *
     * Falls der exakte Name des Datenformats des Inhalts dieser HTTP-Response benötigt wird, den der Server gemeldet hat, muss der Wert des
     * HTTP-Headers `Content-Type` via [getHeaderValue] ausgelesen werden.
     */
    fun getContentType(): XMLHttpRequestResponseType {
        return httpRequest.responseType
    }

    /**
     * Gibt den Inhalt dieser HTTP-Response als Text zurück.
     */
    fun getTextContent(): String {
        return httpRequest.responseText
    }

    /**
     * Gibt den Inhalt dieser HTTP-Response als ein Objekt zurück, dessen Typ und Struktur vom Webbrowser bestimmt wird.
     */
    fun getRawContent(): Any? {
        return httpRequest.response
    }
}