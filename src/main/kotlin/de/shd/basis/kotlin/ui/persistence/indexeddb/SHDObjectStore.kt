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
 * Diese Klasse repräsentiert einen `ObjectStore`, der sich in einer lokalen Datenbank befindet, welche sich widerum im Webbrowser befindet (siehe
 * [SHDDatabase]). In einem `ObjectStore` liegen die eigentlichen, persistierten Daten in Form von Objekten. Die Datenbank selbst ist mehr oder
 * weniger  nur ein Container beliebig vieler `ObjectStores`. Daher können persistierte Daten nur über die API eines `ObjectStores` gespeichert,
 * ausgelesen, aktualisiert und gelöscht werden.
 *
 * Diese Klasse fungiert dabei als Wrapper der nativen API eines `ObjectStores` (siehe [IDBObjectStore]). Sie vereinfacht und vereinheitlicht häufige
 * bzw. einfache CRUD-Operationen. Auch kapselt diese Klasse, wo möglich, das Transaktions-Handling von Operationen. Allerdings ermöglicht sie auch
 * das Erzeugen eigener Transaktionen.
 *
 * Instanzen von dieser Klasse sollen ausschließlich indirekt über den [SHDDatabaseBuilder] erzeugt werden.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SHDObjectStore internal constructor(private val name: String, private val indexedDB: IDBDatabase) {

    /**
     * Erzeugt asynchron einen [SHDObjectIterator], der es ermöglicht, über alle Objekte, die in diesem `ObjectStore` enthalten sind, zu iterieren.
     * Dabei kann über diesen Iterator gesteuert werden, ob und wann neue Objekte nachgeladen werden sollen.
     *
     * Sobald über die Objekte iteriert werden kann, wird die Methode [RepeatablePromise.then] des zurückgegebenen [Promises][RepeatablePromise] mit
     * dem erzeugten [SHDObjectIterator] als Argument aufgerufen. Falls ein Fehler auftritt, wird stattdessen die Methode [RepeatablePromise.catch]
     * mit einer [SHDRuntimeException] als Argument aufgerufen.
     */
    fun iterator(): RepeatablePromise<SHDObjectIterator> {
        return openCursor(undefined, null)
    }

    /**
     *
     */
    fun iterator(query: String): RepeatablePromise<SHDObjectIterator> {
        return openCursor(query, null)
    }

    /**
     *
     */
    fun iterator(query: IDBKeyRange): RepeatablePromise<SHDObjectIterator> {
        return openCursor(query, null)
    }

    /**
     *
     */
    fun iterator(index: SHDStoreIndex): RepeatablePromise<SHDObjectIterator> {
        return openCursor(undefined, index)
    }

    /**
     *
     */
    fun iterator(index: SHDStoreIndex, query: String): RepeatablePromise<SHDObjectIterator> {
        return openCursor(query, index)
    }

    /**
     *
     */
    fun iterator(index: SHDStoreIndex, query: IDBKeyRange): RepeatablePromise<SHDObjectIterator> {
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
    fun clear(): RepeatablePromise<Nothing?> {
        return DefaultRepeatablePromise { invokeThen, invokeCatch ->
            doInTransaction(IDBTransactionMode.READWRITE) { store ->
                val clearRequest = store.clear()

                clearRequest.onerror = { invokeCatch(SHDRuntimeException("Der ObjectStore '$name' konnte nicht geleert werden")) }
                clearRequest.onsuccess = { invokeThen(null) }
            }
        }
    }

    /**
     *
     */
    private fun openCursor(query: Any?, index: SHDStoreIndex?): RepeatablePromise<SHDObjectIterator> {
        return DefaultRepeatablePromise { invokeThen, invokeCatch ->
            doInTransaction(IDBTransactionMode.READONLY) { store ->
                val cursorRequest = openCursor(query, index, store)

                cursorRequest.onerror = { invokeCatch(SHDRuntimeException("Es konnten keine Daten aus dem ObjectStore '$name' ausgelesen werden")) }
                cursorRequest.onsuccess = { invokeThen(createIterator(it)) }
            }
        }
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
    private fun createIterator(event: Event): SHDObjectIterator {
        return SHDObjectIterator(extractRequest(event).result as IDBCursorWithValue?)
    }

    /**
     *
     */
    private fun extractRequest(event: Event): IDBRequest {
        return event.target as IDBRequest
    }
}