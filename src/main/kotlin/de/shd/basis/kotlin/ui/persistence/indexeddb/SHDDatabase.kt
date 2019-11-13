package de.shd.basis.kotlin.ui.persistence.indexeddb

import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import org.w3c.dom.indexedDB
import org.w3c.indexeddb.IDBDatabase
import org.w3c.indexeddb.IDBFactory
import kotlin.js.Promise

/**
 * Diese Klasse repräsentiert eine lokale Datenbank, die sich im Webbrowser befindet und auch von dieser verwaltet wird. Das bedeutet, die
 * Möglichkeiten und Beschränkungen (wie z.B. der verfügbare Speicherplatz), werden individuell vom Webbrowser festgelegt.
 *
 * Technisch basiert diese Datenbank-API auf der [IndexedDB-API](https://developer.mozilla.org/en-US/docs/Web/API/IndexedDB_API), kapselt aber ihre
 * Komplexität. Und bei der zugrunde liegenden Datenbank handelt es sich um eine [(NoSQL-)Objektdatenbank](https://de.wikipedia.org/wiki/Objektdatenbank)
 * und nicht um eine relationale Datenbank.
 *
 * Dementsprechend basiert die Struktur dieser Datenbank nicht auf Schemas und Tabellen, sondern auf einzelnen, sog. `ObjectStores`, in denen sich die
 * persistierten Datenobjekte befinden.
 *
 * D.h. um Daten(-objekte) speichern und auslesen zu können, muss zunächst eine Verbindung zu einer `IndexedDB` hergestellt und anschließend darüber
 * die benötigten, zugehörigen `ObjectStores` ermittelt werden. Falls die `ObjectStores` noch nicht existieren, müssen sie explizit angelegt werden.
 * Anschließend können Daten in Form von Objekten in `ObjectStores` abgelegt, daraus ausgelesen und auch wieder entfernt werden.
 *
 * Damit Anwendungen die Referenzen auf `ObjectStores` nicht selbst halten und verwalten müssen, kümmert sich diese Klasse um die Verwaltung der
 * verfügbaren `ObjectStores`. Auf den jeweiligen `ObjectStore` kann über einen Enum-Wert zugegriffen werden, der ihm zuvor zugewiesen worden sein
 * muss. Der generischer Parameter dieser Klasse gibt an, mit welchem `ObjectStore`-Enum diese Datenbank-API arbeitet. Dabei steht jeder Enum-Wert
 * für genau einen `ObjectStore`.
 *
 * Eine Webanwendung (bzw. Webseite) kann theoretisch beliebig viele Datenbanken anlegen, welche wiederum theoretisch beliebig viele `ObjectStores`
 * enthalten können. Der einzige, limitierende Faktor, ist der Speicherplatz, den der Webbrwoser einer Webanwendung (bzw. Webseite) gewährt.
 *
 * Instanzen von dieser Klasse sollen nicht manuell, sondern ausschließlich über den [SHDDatabaseBuilder] erzeugt werden.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SHDDatabase<STORE : Enum<STORE>> internal constructor(private val databaseName: String, private val database: IDBDatabase, private val storeMap: Map<STORE, SHDObjectStore>) {

    /**
     * Gibt den [SHDObjectStore] zurück, der dem übergebenen Enum-Wert zugewiesen wurde. Falls diesem Enum-Wert (noch) kein [SHDObjectStore]
     * zugewiesen wurde, wird eine [NoSuchElementException] geworfen.
     */
    fun getStore(storeType: STORE): SHDObjectStore {
        return storeMap.getOrElse(storeType) {
            throw NoSuchElementException("Es wurde (noch) kein ObjectStore vom Typ '$storeType' zur Datenbank hinzugefuegt")
        }
    }

    /**
     * TODO Dokumentation
     */
    fun close() {
        database.close()
    }

    /**
     * Löscht diese Datenbank unwiderruflich.
     *
     * @see IDBFactory.deleteDatabase
     */
    fun delete(): Promise<Nothing?> {

        // Schließt vorher die Verbindung zur Datenbank
        close()

        return Promise { resolve, reject ->
            val deleteRequest = indexedDB.deleteDatabase(databaseName)

            deleteRequest.onerror = { reject(SHDRuntimeException("Die Datenbank '$databaseName' konnte nicht geloescht werden")) }
            deleteRequest.onblocked = { reject(SHDRuntimeException("Die Datenbank '$databaseName' konnte nicht geloescht werden - Die Datenbankverbindung ist noch offen")) }
            deleteRequest.onsuccess = { resolve(null) }
        }
    }
}