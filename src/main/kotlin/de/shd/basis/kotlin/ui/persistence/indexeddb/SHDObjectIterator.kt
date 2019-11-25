package de.shd.basis.kotlin.ui.persistence.indexeddb

import org.w3c.indexeddb.IDBCursorWithValue

/**
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SHDObjectIterator internal constructor(private val cursor: IDBCursorWithValue?) {

    /**
     *
     */
    fun getKey(): dynamic {
        return cursor?.key
    }

    /**
     *
     */
    fun getValue(): dynamic {
        return cursor?.value
    }

    /**
     *
     */
    fun reachedEndOfResult(): Boolean {
        return cursor == null
    }

    /**
     *
     */
    fun next() {
        if (reachedEndOfResult()) {
            throw NoSuchElementException("Es gibt kein weiteres Element")
        }

        cursor?.`continue`(undefined)
    }
}