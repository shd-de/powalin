package de.shd.basis.kotlin.ui.persistence.indexeddb

import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import kotlin.js.Promise

/**
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
class SHDDatabaseConnector<STORE : Enum<STORE>> internal constructor(private val onUpgradePromise: Promise<SHDDatabase<STORE>>, private val onSuccessPromise: Promise<SHDDatabase<STORE>>) {

    private var errorHandler: ((SHDRuntimeException) -> Unit)? = null

    fun onUpgrade(onUpgrade: (SHDDatabase<STORE>) -> Unit): SHDDatabaseConnector<STORE> {
        onUpgradePromise.then(onUpgrade).catch { propagateError(SHDRuntimeException("Upgrade der lokalen IndexedDB ist fehlgeschlagen")) }
        return this
    }

    fun onSuccess(onSuccess: (SHDDatabase<STORE>) -> Unit): SHDDatabaseConnector<STORE> {
        onSuccessPromise.then(onSuccess)
        return this
    }

    fun onError(onError: (SHDRuntimeException) -> Unit): SHDDatabaseConnector<STORE> {
        this.errorHandler = onError
        return this
    }

    internal fun propagateError(error: SHDRuntimeException) {
        errorHandler?.invoke(error)
    }
}