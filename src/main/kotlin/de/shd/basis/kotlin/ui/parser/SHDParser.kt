package de.shd.basis.kotlin.ui.parser

import kotlinx.serialization.DeserializationStrategy

/**
 * Ein Interface für Parser von textuellen Datenformaten, die auf Basis solcher Formate Objekte deserialisieren können.
 *
 * @author Florian Steitz (fst)
 */
interface SHDParser {

    /**
     * Parst das übergebene, textuelle Datenformat und konvertiert es auf Basis der übergebenen [DeserializationStrategy] in ein Datenobjekt vom
     * erwarteten Typ.
     */
    fun <DATATYPE> parse(content: String, deserializationStrategy: DeserializationStrategy<DATATYPE>): DATATYPE
}