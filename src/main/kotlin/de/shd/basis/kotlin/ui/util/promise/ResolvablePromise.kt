package de.shd.basis.kotlin.ui.util.promise

import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import kotlin.js.Promise

/**
 * Ein `Promise` repräsentiert das Ergebnis einer asynchronen Ausführung einer Berechnung bzw. Operation. Die API und Funktionsweise dieser Klasse
 * orientiert sich an der Standard-Klasse [Promise], stellt aber nur die Methode [then] und keine [Catch-Methode][Promise.catch] zur Verfügung.
 *
 * Dieses Interface soll aber nur in Ausnahmefällen direkt verwendet werden. Denn es dient primär als Rückgabewert von Operationen, die nicht
 * fehlschlagen können und daher keine Fehlerbehandlung benötigen. Dennoch steht es Implementierungen frei, eine [Catch-Methode][Promise.catch] für
 * mögliche Fehlerbehandlungen anzubieten.
 *
 * Generell soll aber, wenn möglich, [Promise] als Ergebnisobjekt von asynchronen Operationen verwendet werden.
 *
 * @author Florian Steitz (fst)
 */
interface ResolvablePromise<VALUE> {

    /**
     * Registriert einen Listener, der mit dem Ergebnis einer asynchronen Operation aufgerufen wird, sobald es verfügbar ist.
     *
     * Es kann nur einmalig ein Listener registriert werden. Falls diese Methode ein weiteres Mal aufgerufen wird, wird eine [SHDRuntimeException]
     * geworfen.
     */
    fun then(then: (VALUE) -> Unit): ResolvablePromise<VALUE>
}