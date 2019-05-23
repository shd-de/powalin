package de.shd.basis.kotlin.ui.persistence.indexeddb

import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import org.w3c.dom.indexedDB
import org.w3c.indexeddb.IDBDatabase
import org.w3c.indexeddb.IDBIndexParameters
import org.w3c.indexeddb.IDBObjectStoreParameters
import kotlin.js.Promise

/**
 * Builder zum Erzeugen von Instanzen von [SHDDatabase]. Dabei muss für jede [SHDDatabase] ein eigener [SHDDatabaseBuilder] verwendet werden.
 *
 * Über diesen Builder kann die zugrunde liegende `IndexedDB` erzeugt sowie konfiguriert und die benötigten `ObjectStores` auf Basis von
 * [Schemas][SHDStoreSchema] bei Bedarf erstellt werden. Darüber hinaus wird die Datenbank-Versionierung und -Migration ebenfalls über diesen Builder
 * gesteuert.
 *
 * Es ist wichtig zu beachten, dass wenn eine neue Datenbank-Version über diesen Builder festgelegt wird, dass dann dafür eine komplett neue, leere
 * Datenbank erzeugt wird. Anschließend müssen manuell die Datenobjekte aus der alten Datenbank(-Version) in die neue migriert werden. Daher sollen
 * Datenbanken nur dann explizit versioniert werden, wenn es notwendig ist.
 *
 * Solange die Standard-Werte dieses Builders nicht über dessen API überschrieben werden, erzeugt dieser Builder standardmäßig eine Datenbank namens
 * "`shd`" und weist ihr die Version `1` zu. D.h. jeder `ObjectStore`, der so von der Anwendung erzeugt wird, befindet sich dadurch in der gleichen
 * Datenbank. Allerdings erzeugt dieser Builder standardmäßig keinen `ObjectStore`, weshalb die über diesen Builder erzeugte [SHDDatabase] nur
 * sinnvoll genutzt werden kann, wenn zuvor über die Methode [withObjectStores] mindestens ein `ObjectStore` explizit erzeugt bzw. initialisiert wurde.
 * Dieser Builder übernimmt dabei die Entscheidung, ob eine `ObjectStore` neu erzeugt werden muss oder ob ein bestehender ausgelesen werden soll.
 *
 * Damit Anwendungen die Referenzen auf die verfügbaren `ObjectStores` nicht selbst halten und verwalten müssen, kümmert sich die über diesen Builder
 * erzeugte [SHDDatabase] um die Verwaltung der verfügbaren `ObjectStores`. Auf den jeweiligen `ObjectStore` kann über den Enum-Wert zugegriffen
 * werden, der ihm beim Aufruf von [withObjectStores] zugewiesen wurde . Der generischer Parameter dieser Klasse gibt an, mit welchem `ObjectStore`-Enum
 * diese Builder-API arbeitet. Dabei steht jeder Enum-Wert für genau einen `ObjectStore`.
 *
 * **Beispiel**
 * ```
 * val articleStoreConfig = SHDStoreConfig(Store.ARTICLE, SHDStoreSchema("articleNumber", listOf(eanIndex)))
 *
 * SHDDatabaseBuilder<Store>()
 *   .withObjectStores(articleStoreConfig) // ObjectStore "article" bei Bedarf auf Basis der übergebenen Konfiguration anlegen.
 *   .build()                              // Verbindung zur Standard-Datenbank "shd" herstellen und bei Bedarf konfigurieren.
 *   .then { database = it }               // Referenz auf die erzeugte SHDDatabase asynchron in einem Feld hinterlegen.
 *   .catch { console.error(it) }          // Fehlerbehandlung.
 * ```
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SHDDatabaseBuilder<STORE : Enum<STORE>>() {

    private val storeConfigs = mutableListOf<SHDStoreConfig<STORE>>()
    private var databaseName = "shd"
    private var databaseVersion = 1

    /**
     * Legt fest, wie die Datenbank heißt, mit der dieser Builder eine Verbindung herstellen und die er bei Bedarf konfigurieren bzw. initialisieren soll.
     */
    fun withDatabaseName(name: String): SHDDatabaseBuilder<STORE> {
        databaseName = name
        return this
    }

    /**
     * Legt fest, welche Version die Datenbank hat, mit der dieser Builder eine Verbindung herstellen und die er bei Bedarf konfigurieren bzw.
     * initialisieren soll.
     *
     * Es ist wichtig zu beachten, dass wenn hierüber eine neue Version definiert wird, dass dieser Builder dann eine neue, leere Datenbank anlegt.
     * Daten aus anderen bzw. älteren Datenbank-Versionen müssen anschließend manuell in die neue Datenbank-Version migriert werden.
     */
    fun withDatabaseVersion(version: Int): SHDDatabaseBuilder<STORE> {
        databaseVersion = version
        return this
    }

    /**
     * Legt auf Basis der übergebenen [ObjectStore-Konfigurationen][SHDStoreConfig] fest, welche `ObjectStores` die zu erzeugende [SHDDatabase] haben
     * soll. Es muss dabei ein Konfigurationsobjekt pro `ObjectStore` übergeben werden. Ein solches Konfigurationsobjekt weist u.a. einem `ObjectStore`
     * den Enum-Wert zu, der später an die API von [SHDDatabase] übergeben werden muss, um auf den `ObjectStore` zugreifen zu können.
     *
     * Falls nicht alle angegebenen `ObjectStores` in der zugrunde liegenden Datenbank vorhanden sind, werden sie automatisch bei Bedarf auf Basis der
     * entsprechenden [Schemas][SHDStoreSchema] angelegt, die in den Konfigurationsobjekten enthalten sind. Diese Methode aktualisiert allerdings
     * keine `ObjectStores` nachträglich, weil dies technisch ohne eine neue Datenbank-Version oder ohne das Löschen des `ObjectStores` nicht möglich
     * ist. D.h. falls ein `ObjectStore` bereits in der zugrunde liegenden Datenbank existiert, wird das Schema im Konfigurationsobjekt ignoriert.
     * Auch wenn das Schema-Objekt anders als das Schema-Objekt aufgebaut ist, das bei der initialen Anlage des `ObjectStores` angegeben wurde.
     */
    fun withObjectStores(vararg storeConfigs: SHDStoreConfig<STORE>): SHDDatabaseBuilder<STORE> {
        this.storeConfigs.addAll(storeConfigs)
        return this
    }

    /**
     * Stellt asynchron eine Verbindung zur Standard-Datenbank (oder zur konfigurierten Datenbank) her, konfiguriert bzw. initialisiert sie falls
     * notwendig und erzeugt bei Bedarf auch die konfigurierten `ObjectStores`. Sobald dieser asynchrone Prozess abgeschlossen ist, wird die Methode
     * [Promise.then] des zurückgegebenen [Promises][Promise] mit der von diesem Builder erstellten Instanz von [SHDDatabase] als Argument aufgerufen.
     * Dieses Datenbankobjekt repräsentiert dabei die Datenbankverbindung und ermöglicht Zugriffe auf die konfigurierten `ObjectStores`.
     *
     * Falls keine Verbindung zur Datenbank hergestellt werden konnte oder ein anderer Fehler auftrat, wird die Methode [Promise.catch] des
     * zurückgegebenen [Promises][Promise] mit einer [SHDRuntimeException] als Argument aufgerufen.
     */
    fun build(): Promise<SHDDatabase<STORE>> {
        return Promise { resolve, reject ->
            val openDBRequest = indexedDB.open(databaseName, databaseVersion)

            openDBRequest.onupgradeneeded = {
                resolveWithNewStores(openDBRequest.result as IDBDatabase, resolve)
            }

            openDBRequest.onsuccess = {
                resolveWithExistingStores(openDBRequest.result as IDBDatabase, resolve)
            }

            openDBRequest.onerror = {
                reject(SHDRuntimeException("Es konnte keine Verbindung zur lokalen IndexedDB aufgebaut werden"))
            }
        }
    }

    /**
     *
     */
    private fun resolveWithExistingStores(indexedDB: IDBDatabase, resolve: (SHDDatabase<STORE>) -> Unit) {
        return resolve({ config -> SHDObjectStore(toStoreName(config.storeType), indexedDB) }, resolve)
    }

    /**
     *
     */
    private fun resolveWithNewStores(indexedDB: IDBDatabase, resolve: (SHDDatabase<STORE>) -> Unit) {
        return resolve({ config -> createNewObjectStore(config.storeType, config.storeSchema, indexedDB) }, resolve)
    }

    /**
     *
     */
    private fun resolve(storeMapper: (SHDStoreConfig<STORE>) -> SHDObjectStore, resolve: (SHDDatabase<STORE>) -> Unit) {
        return resolve(SHDDatabase(storeConfigs.associateBy({ config -> config.storeType }, storeMapper)))
    }

    /**
     *
     */
    private fun createNewObjectStore(storeType: STORE, storeSchema: SHDStoreSchema, indexedDB: IDBDatabase): SHDObjectStore {
        val storeName = toStoreName(storeType)
        val storeParameters = createStoreParameters(storeSchema.primaryKeyProperty)
        val objectStore = indexedDB.createObjectStore(storeName, storeParameters)

        storeSchema.indices.forEach { index ->
            objectStore.createIndex(index.name, index.propertyToIndex, createIndexParameters(index.multiEntry))
        }

        return SHDObjectStore(storeName, indexedDB)
    }

    /**
     *
     */
    private fun toStoreName(storeType: STORE): String {
        return storeType.name.toLowerCase()
    }

    /**
     *
     */
    private fun createStoreParameters(keyPath: String): IDBObjectStoreParameters {
        return object : IDBObjectStoreParameters {
            override var keyPath: dynamic
                get() = keyPath
                set(value) {
                    super.keyPath = value
                }
        }
    }

    /**
     *
     */
    private fun createIndexParameters(multiEntry: Boolean): IDBIndexParameters {
        return object : IDBIndexParameters {
            override var multiEntry: Boolean?
                get() = multiEntry
                set(value) {
                    super.multiEntry = value
                }
        }
    }
}