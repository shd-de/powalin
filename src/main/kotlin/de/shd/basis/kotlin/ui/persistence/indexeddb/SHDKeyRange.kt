package de.shd.basis.kotlin.ui.persistence.indexeddb

import org.w3c.indexeddb.IDBKeyRange

/**
 *
 * @author Florian Steitz (fst)
 */
class SHDKeyRange(internal val idbKeyRange: IDBKeyRange) {

    /**
     * Statische Builder-API.
     */
    @Suppress("unused", "MemberVisibilityCanBePrivate")
    companion object {

        /**
         * Erzeugt eine Instanz von dieser Klasse auf Basis der übergebenen [IDBKeyRange].
         */
        fun withIDBKeyRange(idbKeyRange: IDBKeyRange): SHDKeyRange {
            return SHDKeyRange(idbKeyRange)
        }

        /**
         * Siehe [IDBKeyRange.only](https://developer.mozilla.org/en-US/docs/Web/API/IDBKeyRange/only).
         */
        fun withOnly(value: Any): SHDKeyRange {
            return withIDBKeyRange(IDBKeyRange.only(value))
        }

        /**
         * Siehe [IDBKeyRange.lowerBound](https://developer.mozilla.org/en-US/docs/Web/API/IDBKeyRange/lowerBound).
         */
        fun withLowerBound(lowerValue: Any, open: Boolean = false): SHDKeyRange {
            return withIDBKeyRange(IDBKeyRange.lowerBound(lowerValue, open))
        }

        /**
         * Siehe [IDBKeyRange.upperBound](https://developer.mozilla.org/en-US/docs/Web/API/IDBKeyRange/upperBound).
         */
        fun withUpperBound(upperValue: Any, open: Boolean = false): SHDKeyRange {
            return withIDBKeyRange(IDBKeyRange.upperBound(upperValue, open))
        }

        /**
         * Siehe [IDBKeyRange.bound](https://developer.mozilla.org/en-US/docs/Web/API/IDBKeyRange/bound).
         */
        fun withBound(lowerValue: Any, upperValue: Any, lowerOpen: Boolean = false, upperOpen: Boolean = false): SHDKeyRange {
            return withIDBKeyRange(IDBKeyRange.bound(lowerValue, upperValue, lowerOpen, upperOpen))
        }

        /**
         * Erzeugt eine Instanz von dieser Klasse, die den übergebenen Mindestwert als `lowerBound` und den übergebenen Mindestwert, konkateniert mit
         * dem größten Wert vom Typ [Char], als `upperBound` festlegt.
         *
         * @see Char.MAX_VALUE
         */
        fun withLowerToMaxBound(lowerValue: String): SHDKeyRange {
            return withBound(lowerValue, lowerValue + Char.MAX_VALUE)
        }

        /**
         * Erzeugt eine Instanz von dieser Klasse, die den übergebenen String in Großbuchstaben als `lowerBound` und den übergebenen String in
         * Kleinbuchstaben, konkateniert mit dem größten Wert vom Typ [Char], als `upperBound` festlegt.
         *
         * Die zu erzeugende [SHDKeyRange] beginnt mit Großbuchstaben, weil deren numerischer Wert kleiner ist, als der von Kleinbuchstaben.
         *
         * @see Char.MAX_VALUE
         */
        fun startsWithIgnoringCase(value: String): SHDKeyRange {
            return withBound(value.toUpperCase(), value.toLowerCase() + Char.MAX_VALUE)
        }
    }
}