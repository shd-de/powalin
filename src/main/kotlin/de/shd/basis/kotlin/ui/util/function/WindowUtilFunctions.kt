package de.shd.basis.kotlin.ui.util.function

import org.w3c.workers.CacheStorage
import kotlin.browser.window
import kotlin.js.Promise

private val cacheStorage = window.caches // Sprechenderen Alias definieren.

/**
 * Iteriert über alle Caches, die zum Origin (Hostname und Port) dieser Anwendung gehören und löscht dabei jeden Cache unwiderruflich. Sobald alle
 * Caches erfolgreich gelöscht wurden, wird die Methode [Promise.then] des zurückgegebenen [Promises][Promise] aufgerufen. Falls aber beim Löschen der
 * Caches ein Fehler auftritt, wird stattdessen die Methode [Promise.catch] mit einer entsprechenden Exception aufgerufen.
 *
 *
 * **Achtung:**
 *
 * Dieser Ansatz kann zu Problemen bzw. Konflikten führen, wenn mehrere Anwendungen den gleichen Origin verwenden. Da aber Anwendungen, die auf
 * diesem Framework basieren, generell als Microservices verteilt werden sollen, sollte dies für die meisten Anwendungen kein Problem darstellen.
 *
 * @see CacheStorage.delete
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun clearCacheStorage(): Promise<Nothing?> {
    return Promise { resolve, reject ->
        cacheStorage.keys()
                .then<Array<String>, Unit> { keys -> deleteCaches(keys, resolve) }
                .catch(reject)
    }
}

/**
 * Iteriert über die übergebenen Cache-Namen und löscht dabei die entsprechenden Caches unwiderruflich. Sobald alle Caches erfolgreich gelöscht wurden,
 * wird die übergebene Funktion `resolve` eines [Promises][Promise] aufgerufen.
 */
private fun deleteCaches(keys: Array<String>, resolve: (Nothing?) -> Unit) {
    keys.forEach { cacheStorage.delete(it) }
    resolve(null)
}