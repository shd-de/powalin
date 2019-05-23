package de.shd.basis.kotlin.ui.persistence.indexeddb

import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import de.shd.basis.kotlin.ui.util.promise.DefaultRepeatablePromise
import de.shd.basis.kotlin.ui.util.promise.RepeatablePromise
import org.w3c.dom.events.Event
import org.w3c.indexeddb.IDBCursorWithValue
import org.w3c.indexeddb.IDBDatabase
import org.w3c.indexeddb.IDBKeyRange
import org.w3c.indexeddb.IDBObjectStore
import org.w3c.indexeddb.IDBRequest
import org.w3c.indexeddb.IDBTransaction
import org.w3c.indexeddb.IDBTransactionMode
import org.w3c.indexeddb.READONLY
import org.w3c.indexeddb.READWRITE

/**
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SHDObjectStore internal constructor(private val name: String, private val indexedDB: IDBDatabase) {

    /**
     *
     */
    fun query(): RepeatablePromise<SHDQueryResultIterator> {
        return openCursor(undefined, null)
    }

    /**
     *
     */
    fun query(query: String): RepeatablePromise<SHDQueryResultIterator> {
        return openCursor(query, null)
    }

    /**
     *
     */
    fun query(query: IDBKeyRange): RepeatablePromise<SHDQueryResultIterator> {
        return openCursor(query, null)
    }

    /**
     *
     */
    fun query(index: SHDStoreIndex): RepeatablePromise<SHDQueryResultIterator> {
        return openCursor(undefined, index)
    }

    /**
     *
     */
    fun query(index: SHDStoreIndex, query: String): RepeatablePromise<SHDQueryResultIterator> {
        return openCursor(query, index)
    }

    /**
     *
     */
    fun query(index: SHDStoreIndex, query: IDBKeyRange): RepeatablePromise<SHDQueryResultIterator> {
        return openCursor(query, index)
    }

    /**
     *
     */
    fun put(value: Any) {
        doInTransaction(IDBTransactionMode.READWRITE) { store -> store.put(value) }
    }

    /**
     *
     */
    fun putAll(values: Collection<Any>) {
        doInTransaction(IDBTransactionMode.READWRITE) { store -> values.forEach { store.put(it) } }
    }

    /**
     *
     */
    fun doInTransaction(transactionMode: IDBTransactionMode, accessStore: (IDBObjectStore) -> Unit) {
        doInTransaction(transactionMode) { _, objectStore -> accessStore(objectStore) }
    }

    /**
     *
     */
    fun doInTransaction(transactionMode: IDBTransactionMode, accessStore: (IDBTransaction, IDBObjectStore) -> Unit) {
        val transaction = indexedDB.transaction(name, transactionMode)
        accessStore(transaction, transaction.objectStore(name))
    }

    /**
     *
     */
    private fun openCursor(query: Any?, index: SHDStoreIndex?): RepeatablePromise<SHDQueryResultIterator> {
        val promise = DefaultRepeatablePromise<SHDQueryResultIterator>()

        doInTransaction(IDBTransactionMode.READONLY) { store ->
            val cursor = openCursor(query, index, store)

            cursor.onerror = { promise.invokeCatch(SHDRuntimeException("Es konnten keine Daten aus dem ObjectStore '$name' ausgelesen werden")) }
            cursor.onsuccess = { promise.invokeThen(createQueryResultIterator(it)) }
        }

        return promise
    }

    /**
     *
     */
    private fun openCursor(query: Any?, index: SHDStoreIndex?, objectStore: IDBObjectStore): IDBRequest {
        return if (index == null) objectStore.openCursor(query) else objectStore.index(index.name).openCursor(query)
    }

    /**
     *
     */
    private fun createQueryResultIterator(event: Event): SHDQueryResultIterator {
        return SHDQueryResultIterator(extractRequest(event).result as IDBCursorWithValue?)
    }

    /**
     *
     */
    private fun extractRequest(event: Event): IDBRequest {
        return event.target as IDBRequest
    }
}