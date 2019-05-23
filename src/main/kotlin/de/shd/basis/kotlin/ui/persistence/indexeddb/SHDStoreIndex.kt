package de.shd.basis.kotlin.ui.persistence.indexeddb

/**
 *
 * @author Florian Steitz (fst)
 */
data class SHDStoreIndex(
        val name: String,
        val propertyToIndex: String,
        val multiEntry: Boolean) {

    /**
     *
     */
    @Suppress("unused")
    constructor(indexName: String, propertyToIndex: String) : this(indexName, propertyToIndex, false)
}