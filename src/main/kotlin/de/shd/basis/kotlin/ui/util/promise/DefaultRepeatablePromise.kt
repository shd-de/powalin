package de.shd.basis.kotlin.ui.util.promise

import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException

/**
 * Die Standard-Implementierung von [RepeatablePromise], die das (mehrmalige) Aufrufen registrierter Listener ermöglicht.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class DefaultRepeatablePromise<VALUE>(private val executor: (invokeThen: (VALUE) -> Unit, invokeCatch: (Throwable) -> Unit) -> Unit) : RepeatablePromise<VALUE> {

    private var then: ((VALUE) -> Unit)? = null
    private var catch: ((Throwable) -> Unit)? = null
    private var startedExecution = false

    override fun then(then: (VALUE) -> Unit): DefaultRepeatablePromise<VALUE> {
        requireNull(this.then)
        this.then = then
        ensureExecutionHasStarted()

        return this
    }

    override fun catch(catch: (Throwable) -> Unit): DefaultRepeatablePromise<VALUE> {
        requireNull(this.catch)
        this.catch = catch
        ensureExecutionHasStarted();

        return this
    }

    /**
     * Ruft den via [then] registrierten Listener mit dem übergebenen Ergebnis der asynchronen Operation auf, falls ein solcher Listener registriert
     * wurde.
     */
    fun invokeThen(value: VALUE) {
        then?.invoke(value)
    }

    /**
     * Ruft den via [catch] registrierten Listener mit dem übergebenen `Throwable` der fehlgeschlagenen, asynchronen Operation auf, falls ein solcher
     * Listener registriert wurde.
     */
    fun invokeCatch(throwable: Throwable) {
        catch?.invoke(throwable)
    }

    /**
     * Stellt sicher, dass der [executor] spätestens durch Aufruf dieser Methode ausgeführt wird. Falls er bereits ausgeführt wird oder wurde, tut
     * diese Methode nichts.
     */
    private fun ensureExecutionHasStarted() {
        if (!startedExecution) {
            executor.invoke(this::invokeThen, this::invokeCatch)
            startedExecution = true
        }
    }

    /**
     * Wirft einer [SHDRuntimeException], falls der übergebene Listener **nicht** `null` ist.
     */
    private fun <ARGUMENT> requireNull(listener: ((ARGUMENT) -> Unit)?) {
        if (listener != null) {
            throw SHDRuntimeException("Es wurde bereits ein Listener registriert")
        }
    }
}