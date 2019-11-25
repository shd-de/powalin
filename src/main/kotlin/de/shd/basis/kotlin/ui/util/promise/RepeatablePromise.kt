package de.shd.basis.kotlin.ui.util.promise

import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import kotlin.js.Promise

/**
 * Ein `Promise` repräsentiert das Ergebnis einer asynchronen Ausführung einer Berechnung bzw. Operation. Die API und Funktionsweise dieser Klasse
 * orientiert sich an der Standard-Klasse [Promise], ermöglicht es aber, via [then] oder [catch] registrierte listener beliebig oft aufzurufen. D.h.
 * dieses Promise kann nicht "beendet" bzw. final "erfüllt" werden.
 *
 * Implementierungen von diesem Interface sollen aber nur in Ausnahmefällen verwendet werden, z.B. wenn Listener rekursiv aufgerufen werden müssen.
 * Wenn möglich, soll [Promise] als Ergebnisobjekt von asynchronen Operationen verwendet werden.
 *
 * @author Florian Steitz (fst)
 */
interface RepeatablePromise<VALUE> : ResolvablePromise<VALUE> {

    /**
     * Registriert einen Listener, der immer dann mit einem Ergebnis der asynchronen Operation aufgerufen wird, wenn ein (neues) Ergebnis verfügbar
     * ist.
     *
     * Es kann nur einmalig ein Listener registriert werden. Falls diese Methode ein weiteres Mal aufgerufen wird, wird eine [SHDRuntimeException]
     * geworfen.
     */
    override fun then(then: (VALUE) -> Unit): RepeatablePromise<VALUE>

    /**
     * Registriert einen Listener, der mit einem `Throwable` aufgerufen wird, sobald die asynchrone Operation fehlschlägt.
     *
     * Es kann nur einmalig ein Listener registriert werden. Falls diese Methode ein weiteres Mal aufgerufen wird, wird eine [SHDRuntimeException]
     * geworfen.
     */
    fun catch(catch: (Throwable) -> Unit): RepeatablePromise<VALUE>
}