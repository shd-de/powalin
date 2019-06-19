package de.shd.basis.kotlin.ui.parser

import de.shd.basis.kotlin.ui.serialization.generator.SHDGenerator
import de.shd.basis.kotlin.ui.serialization.parser.SHDParser
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

/**
 * Die Standard-Implementierung von [SHDGenerator] sowie [SHDParser] zum Serialisieren und Deserialisieren von textuellen JSON-Datenstrukturen. Intern
 * setzt sie dabei auf API von [Json].
 *
 * Instanzen von dieser Klasse können entweder mit der [Standard-Konfiguration][JsonConfiguration.Stable] oder einer individuellen
 * [Konfiguration][JsonConfiguration] erzeugt werden. Ein solches Konfigurationsobjekt steuert u.a., wie sich dieser Konvertierer in bestimmten
 * Situationen verhalten soll.
 *
 * Nähere Details zur API von [Json] können [hier](https://github.com/Kotlin/kotlinx.serialization) nachgelesen werden.
 *
 * @author Florian Steitz (fst)
 */
class SHDJSONParser(configuration: JsonConfiguration) : SHDParser, SHDGenerator {

    private val json = Json(configuration)

    /**
     * Initialisiert diesen Konvertierer mit den Standard-Konfigurationen, die der primäre Konstruktor von [JsonConfiguration] festlegt. Die einzige
     * Abweichung ist, dass der strikte Modus deaktiviert wird, wodurch Klassen nicht für jedes JSON-Property notwendigerweise ein entsprechendes
     * Property zur Verfügung stellen müssen.
     */
    @Suppress("EXPERIMENTAL_API_USAGE") // Der Konstruktor wird von dieser Klasse gekapselt, daher sind API-Änderungen nicht kritisch.
    constructor() : this(JsonConfiguration(strictMode = false))

    /**
     * Parst die übergebene, textuelle JSON-Datenstruktur und konvertiert sie auf Basis der übergebenen [DeserializationStrategy] in ein Datenobjekt
     * vom erwarteten Typ.
     */
    override fun <DATATYPE> parse(content: String, deserializationStrategy: DeserializationStrategy<DATATYPE>): DATATYPE {
        return json.parse(deserializationStrategy, content)
    }

    /**
     * Konvertiert das übergebene Objekt auf Basis der übergebenen [SerializationStrategy] in eine entsprechende, textuelle JSON-Datenstruktur.
     */
    override fun <DATATYPE> generate(dataType: DATATYPE, serializationStrategy: SerializationStrategy<DATATYPE>): String {
        return json.stringify(serializationStrategy, dataType)
    }
}