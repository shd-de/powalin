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
open class SHDRuntimeException constructor(message: String, cause: Throwable?) : RuntimeException(message, cause) {

    /**
     * Erzeugt eine Instanz von dieser Exception mit der übergebenen Fehlermeldung.
     */
    constructor(message: String) : this(message, null)
}