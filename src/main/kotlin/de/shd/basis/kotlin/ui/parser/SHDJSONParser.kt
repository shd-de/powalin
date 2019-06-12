package de.shd.basis.kotlin.ui.parser

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

/**
 * Die Standard-Implementierung von [SHDParser] zum Deserialisieren von textuellen JSON-Datenstrukturen. Intern kapselt sie dabei den Großteil der API
 * von `kotlinx-serialization`.
 *
 * Instanzen von dieser Klasse können entweder mit der [Standard-Konfiguration][JsonConfiguration.Stable] oder einer individuellen
 * [Konfiguration][JsonConfiguration] erzeugt werden. Ein solches Konfigurationsobjekt steuert u.a., wie sich dieser Parser in bestimmten Situationen
 * verhalten soll.
 *
 * Nähere Details zur API von `kotlinx-serialization` können [hier](https://github.com/Kotlin/kotlinx.serialization) nachgelesen werden.
 *
 * @see Json
 * @author Florian Steitz (fst)
 */
class SHDJSONParser(configuration: JsonConfiguration) : SHDParser {

    private val json = Json(configuration)

    /**
     * Initialisiert diesen Parser mit der [Standard-Konfiguration][JsonConfiguration.Stable].
     */
    constructor() : this(JsonConfiguration.Stable)

    /**
     * Parst die übergebene, textuelle JSON-Datenstruktur und konvertiert sie auf Basis der übergebenen [DeserializationStrategy] in ein Datenobjekt
     * vom erwarteten Typ.
     */
    override fun <DATATYPE> parse(content: String, deserializationStrategy: DeserializationStrategy<DATATYPE>): DATATYPE {
        return json.parse(deserializationStrategy, content)
    }
}