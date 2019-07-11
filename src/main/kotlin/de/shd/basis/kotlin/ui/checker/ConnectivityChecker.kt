package de.shd.basis.kotlin.ui.checker

import de.shd.basis.kotlin.ui.util.promise.DefaultRepeatablePromise
import de.shd.basis.kotlin.ui.util.promise.ResolvablePromise
import kotlin.browser.window

/**
 * TODO
 *
 * @author Florian Steitz (fst)
 */
object ConnectivityChecker {

    private val onOnlineListeners = mutableListOf<DefaultRepeatablePromise<Nothing?>>()
    private val onOfflineListeners = mutableListOf<DefaultRepeatablePromise<Nothing?>>()

    /**
     * TODO
     */
    init {
        window.addEventListener("online", { onOnlineListeners.forEach(this::invokeThen) })
        window.addEventListener("offline", { onOfflineListeners.forEach(this::invokeThen) })
    }

    /**
     * TODO
     */
    fun whenOnline(): ResolvablePromise<Nothing?> {
        return addPromise(onOnlineListeners, window.navigator.onLine)
    }

    /**
     * TODO
     */
    fun whenOffline(): ResolvablePromise<Nothing?> {
        return addPromise(onOfflineListeners, !window.navigator.onLine)
    }

    /**
     * TODO
     */
    private fun addPromise(listeners: MutableCollection<DefaultRepeatablePromise<Nothing?>>, invokeImmediately: Boolean): ResolvablePromise<Nothing?> {
        val promise = DefaultRepeatablePromise<Nothing?> { invokeThen, _ -> if (invokeImmediately) invokeThen(null) }
        listeners.add(promise)

        return promise
    }

    /**
     * TODO
     */
    private fun invokeThen(promise: DefaultRepeatablePromise<Nothing?>) {
        promise.invokeThen(null)
    }
}