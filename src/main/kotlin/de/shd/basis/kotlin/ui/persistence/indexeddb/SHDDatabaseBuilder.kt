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
 * Dieser Builder übernimmt dabei die Entscheidung, wann ein `ObjectStore` (neu) erzeugt wird.
 *
 * Es muss aber beachtet werden, dass neue `ObjectStores` nur in der Transaktion neu erzeugt werden können, innerhalb der die Datenbank initialisiert
 * wird. D.h. wenn zu einem späteren Zeitpunkt zusätzliche `ObjectStores` benötigt werden, muss eine neue Datenbank-Version erzeugt und damit
 * (Bestands-)Daten migriert werden.
 *
 * Damit Anwendungen die Referenzen auf die verfügbaren `ObjectStores` nicht selbst halten und verwalten müssen, kümmert sich die über diesen Builder
 * erzeugte [SHDDatabase] um die Verwaltung der verfügbaren `ObjectStores`. Auf den jeweiligen `ObjectStore` kann über den Enum-Wert zugegriffen
 * werden, der ihm beim Aufruf von [withObjectStores] zugewiesen wurde. Der generischer Parameter dieser Klasse gibt an, mit welchem `ObjectStore`-Enum
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
class SHDDatabaseBuilder<STORE : Enum<STORE>> {

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
     * Auch wenn das übergebene Schema-Objekt anders als das Schema-Objekt aufgebaut ist, das bei der initialen Anlage des `ObjectStores` übergeben
     * wurde.
     */
    fun withObjectStores(vararg storeConfigs: SHDStoreConfig<STORE>): SHDDatabaseBuilder<STORE> {
        this.storeConfigs.addAll(storeConfigs)
        return this
    }

    /**
     * Stellt asynchron eine Verbindung zur Standard- bzw. konfigurierten Datenbank her und initialisiert bzw. konfiguriert sie zuvor bei Bedarf,
     * indem bspw. alle konfigurierten `ObjectStores` und Indizes erzeugt werden. Über den zurückgegebenen [SHDDatabaseConnector] kann anschließend
     * auf bestimmte Ereignisse in Form von Listenern reagiert werden. Alle "positiven" Listener erhalten dann die von diesem Builder erstellte
     * Instanz von [SHDDatabase] als Argument.
     *
     *
     * Listener können wie folgt registriert werden:
     * - Via [SHDDatabaseConnector.onUpgrade]: Wird informiert, wenn die Datenbank (oder eine neue Version davon) initialisiert wird.
     * - Via [SHDDatabaseConnector.onSuccess]: Wird informiert, nachdem die Datenbankverbindung hergestellt und die Datenbank ggf. initialisiert wurde.
     * - Via [SHDDatabaseConnector.onError]: Wird informiert, wenn ein Fehler bei der Initialisierung oder bei der Herstellung der Datenbankverbindung
     * auftritt.
     *
     * Das [SHDDatabase]-Objekt, das Listener als Argument erhalten, repräsentiert dabei die eigentliche Datenbankverbindung und ermöglicht u.a.
     * Zugriffe auf die konfigurierten `ObjectStores`.
     */
    fun build(): SHDDatabaseConnector<STORE> {
        val openDBRequest = indexedDB.open(databaseName, databaseVersion)
        val connector = SHDDatabaseConnector<STORE>(
                Promise { resolve, _ -> openDBRequest.onupgradeneeded = { resolveWithNewStores(openDBRequest.result as IDBDatabase, resolve) } },
                Promise { resolve, _ -> openDBRequest.onsuccess = { resolveWithExistingStores(openDBRequest.result as IDBDatabase, resolve) } }
        )

        openDBRequest.onerror = {
            connector.propagateError(SHDRuntimeException("Es konnte keine Verbindung zur lokalen IndexedDB aufgebaut werden"))
        }

        return connector
    }

    /**
     * Erzeugt ein Objekt vom Typ [SHDDatabase], ohne dabei `ObjectStores` in der zugrunde liegenden Datenbank anzulegen. D.h. es wird schlicht ein
     * [SHDObjectStore] pro [SHDStoreConfig] erstellt und auf deren Basis eine Instanz von [SHDDatabase] erzeugt. Diese wird anschließend an die
     * übergebene Funktion `resolve` übergeben.
     *
     * Es gilt zu beachten, dass diese Methode nicht prüft, ob ein `ObjectStore` wirklich existiert. Ihre Implementierung basiert darauf, dass die
     * zugrunde liegende Datenbank bereits vollständig initialisiert wurde. Daher werden bspw. erst Verbindungen zu `ObjectStores` aufgebaut, wenn
     * entsprechende Methoden von [SHDObjectStore] aufgerufen werden.
     */
    private fun resolveWithExistingStores(indexedDB: IDBDatabase, resolve: (SHDDatabase<STORE>) -> Unit) {
        return resolve(indexedDB, { config -> SHDObjectStore(toStoreName(config.storeType), indexedDB) }, resolve)
    }

    /**
     * Erzeugt ein Objekt vom Typ [SHDDatabase] und legt dabei alle konfigurierten `ObjectStores` in der zugrunde liegenden Datenbank an. D.h. es wird
     * ein `ObjectStore` pro [SHDStoreConfig] angelegt. Im Zuge dessen wird auch ein [SHDObjectStore] pro angelegtem `ObjectStore` erstellt, auf deren
     * Basis eine Instanz von [SHDDatabase] erzeugt wird. Diese wird anschließend an die übergebene Funktion `resolve` übergeben.
     *
     * Es gilt zu beachten, dass diese Methode nicht prüft, ob ein `ObjectStore` bereits existiert. Ihre Implementierung basiert darauf, dass die
     * zugrunde liegende Datenbank noch "leer" ist, d.h. gerade frisch angelegt wurde. Daher werden bspw. erst Verbindungen zu `ObjectStores`
     * aufgebaut, wenn entsprechende Methoden von [SHDObjectStore] aufgerufen werden.
     */
    private fun resolveWithNewStores(indexedDB: IDBDatabase, resolve: (SHDDatabase<STORE>) -> Unit) {
        return resolve(indexedDB, { config -> createNewObjectStore(config, indexedDB) }, resolve)
    }

    /**
     * Erzeugt ein Objekt vom Typ [SHDDatabase], indem ein [SHDObjectStore] pro [SHDStoreConfig] durch den übergebenen `storeMapper` erzeugt wird und
     * diese Objekte in Form einer Map an die zu erstellende Instanz von [SHDDatabase] übergeben werden. Dadurch kann nachträglich kein weiterer
     * `ObjectStore` zur [SHDDatabase] hinzugefügt werden.
     *
     * Die erzeugte [SHDDatabase] wird schließlich an die übergebene Funktion `resolve` übergeben.
     */
    private fun resolve(indexedDB: IDBDatabase, storeMapper: (SHDStoreConfig<STORE>) -> SHDObjectStore, resolve: (SHDDatabase<STORE>) -> Unit) {
        return resolve(SHDDatabase(indexedDB, storeConfigs.associateBy({ config -> config.storeType }, storeMapper)))
    }

    /**
     * Legt einen neuen `ObjectStore` auf Basis des übergebenen Konfigurationsobjekts in der übergebenen [IDBDatabase] an und gibt eine neue Instanz
     * von [SHDObjectStore] zurück, die den neu erstellten `ObjectStore` repräsentiert.
     */
    private fun createNewObjectStore(storeConfig: SHDStoreConfig<STORE>, indexedDB: IDBDatabase): SHDObjectStore {
        val storeSchema = storeConfig.storeSchema
        val storeName = toStoreName(storeConfig.storeType)
        val storeParameters = createStoreParameters(storeSchema.primaryKeyProperty)
        val objectStore = indexedDB.createObjectStore(storeName, storeParameters)

        storeSchema.indices.forEach { index ->
            objectStore.createIndex(index.name, index.propertyToIndex, createIndexParameters(index.multiEntry))
        }

        return SHDObjectStore(storeName, indexedDB)
    }

    /**
     * Konvertiert den übergebenen Enum-Wert in den eigentlichen Namen des zugehörigen `ObjectStores`. Ein solcher Name entspricht dabei dem
     * kleingeschriebenem Namen des Enum-Werts.
     */
    private fun toStoreName(storeType: STORE): String {
        return storeType.name.toLowerCase()
    }

    /**
     * Erstellt eine (anonyme) Implementierung von [IDBObjectStoreParameters], die nur den übergebenen `keyPath` enthält.
     */
    private fun createStoreParameters(keyPath: String): IDBObjectStoreParameters {
        return object : IDBObjectStoreParameters {
            override var keyPath: dynamic
                get() = keyPath
                set(_) {}
        }
    }

    /**
     * Erstellt eine (anonyme) Implementierung von [IDBIndexParameters], die nur das übergebene Flag enthält.
     */
    private fun createIndexParameters(multiEntry: Boolean): IDBIndexParameters {
        return object : IDBIndexParameters {
            override var multiEntry: Boolean?
                get() = multiEntry
                set(_) {}
        }
    }
}