package de.shd.basis.kotlin.ui.persistence.indexeddb

/**
 *
 * @author Florian Steitz (fst)
 */
class SHDStoreSchema(
        val primaryKeyProperty: String,
        val indices: Collection<SHDStoreIndex>
)