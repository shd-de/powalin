package de.shd.basis.kotlin.ui.http

import de.shd.basis.kotlin.ui.serialization.parser.SHDParser
import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import kotlinx.serialization.DeserializationStrategy
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType

/**
 * Repräsentiert eine HTTP-Response. Instanzen von dieser Klasse werden ausschließlich durch den [HTTPClient] erzeugt.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class HTTPResponse internal constructor(private val httpRequest: XMLHttpRequest, private val bodyParser: SHDParser?) {

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
     * Gibt den Nachrichtenrumpf (`Body`) dieser HTTP-Response als Text zurück.
     */
    fun getBodyAsText(): String {
        return httpRequest.responseText
    }

    /**
     * Gibt den Nachrichtenrumpf (`Body`) dieser HTTP-Response als ein (fast beliebiges) Objekt zurück, dessen Typ und Struktur vom Webbrowser
     * bestimmt wird und daher erst zur Laufzeit bekannt ist.
     */
    fun getBodyAsAny(): Any? {
        return httpRequest.response
    }

    /**
     * Versucht, den textuellen Nachrichtenrumpf (`Body`) dieser HTTP-Response zu parsen und ihn auf Basis der übergebenen [DeserializationStrategy]
     * in ein Datenobjekt vom erwarteten Typ zu konvertieren. Schließlich wird das so erzeugte Datenobjekt zurückgegeben.
     *
     * Falls kein passender Parser zum Parsen des Nachrichtenrumpfs vorhanden ist, wird eine [SHDRuntimeException] geworfen. Ein passender Parser wird
     * anhand des Werts des HTTP-Headers `content-type` ermittelt, den der Server zurückgemeldet hat.
     */
    fun <DATATYPE> getBody(deserializationStrategy: DeserializationStrategy<DATATYPE>): DATATYPE {
        if (bodyParser == null) {
            throw SHDRuntimeException("Es konnte kein passender Parser fuer den ContentType '${getHeaderValue(HTTPHeaders.CONTENT_TYPE)}' ermittelt werden")
        }

        return bodyParser.parse(getBodyAsText(), deserializationStrategy)
    }
}