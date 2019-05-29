package de.shd.basis.kotlin.ui.util.exception

import kotlin.js.Promise

/**
 * Eine Exception-Klasse, die primär für technische Fehler (und nur bei Bedarf für fachliche Fehler) verwendet werden soll, falls es keine passende,
 * spezifischere Exception-Klasse gibt und die Erstellung einer neuen Exception-Klasse den Aufwand nicht rechtfertigt.
 *
 * Generell sollen aber Exceptions nur in Ausnahmefällen geworfen werden. Wenn möglich, soll stattdessen mit Ergebnisobjekten gearbeiten werden, auf
 * die UI-Komponenten bspw. via Lambda-Ausdrücken reagieren können (wie z.B. ein [Promise]).
 *
 * Eine Instanz von dieser Klasse kann entweder mit einer Fehlermeldung und einer "Verursacher-Throwable" oder nur mit einer Fehlermeldung erzeugt
 * werden. Die Angabe einer Fehlermeldung ist aber in jedem Fall verpflichtend.
 *
 * SHD-spezifische Exception-Klassen sollen von dieser Klasse ableiten.
 *
 * @author Florian Steitz (fst)
 */
open class SHDRuntimeException(message: String, cause: Throwable?) : RuntimeException(message, cause) {

    /**
     * Erzeugt eine Instanz von dieser Exception mit der übergebenen Fehlermeldung.
     */
    constructor(message: String) : this(message, null)
}

/**
 * Eine Exception, die geworfen wird, wenn eine Operation eine festgelegte, maximale Dauer überschritten hat (sprich in einen Timeout gelaufen ist).
 *
 * Dieser Exception soll immer eine Fehlermeldung mitgegeben werden.
 */
open class SHDTimeoutException(message: String) : SHDRuntimeException(message)

/**
 * Eine Exception, die geworfen wird, wenn ein HTTP-Request fehlschlägt (bspw. weil der Server einen Fehler meldet). Ein solche Exception enthält den
 * [HTTP-Status-Code](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status) und den Inhalt der HTTP-Response, die der Server zurückgegeben hat.
 *
 * Auch enthält eine solche Exception eine zusätzliche Fehlermeldung, die lokale APIs immer mitgeben sollen.
 */
@Suppress("unused")
open class SHDHTTPRequestException constructor(val statusCode: Short, val responseContent: String, message: String) : SHDRuntimeException(message)