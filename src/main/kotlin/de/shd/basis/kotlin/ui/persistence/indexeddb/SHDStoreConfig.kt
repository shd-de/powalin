package de.shd.basis.kotlin.ui.persistence.indexeddb

/**
 *
 */
data class SHDStoreConfig<STORE : Enum<STORE>>(
        val storeType: STORE,
        val storeSchema: SHDStoreSchema
)