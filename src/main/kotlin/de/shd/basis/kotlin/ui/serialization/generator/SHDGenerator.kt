package de.shd.basis.kotlin.ui.serialization.generator

import kotlinx.serialization.SerializationStrategy

/**
 * Ein Interface für Generatoren von textuellen Datenformaten, die (beliebige) Objekte in solche Formate serialisieren können.
 *
 * Eine Implementierung von diesem Interface soll stets nur ein einziges Datenformat unterstützen. Denn die API dieses Interfaces sieht explizit nicht
 * vor, dass ein Aufrufer ein Ziel-Datenformat angeben kann.
 *
 * @author Florian Steitz (fst)
 */
interface SHDGenerator {

    /**
     * Konvertiert das übergebene Objekt auf Basis der übergebenen [SerializationStrategy] in das textuelle Datenformat, das die Implementierung
     * dieses Interfaces intern verwendet.
     */
    fun <DATATYPE> generate(dataType: DATATYPE, serializationStrategy: SerializationStrategy<DATATYPE>): String
}