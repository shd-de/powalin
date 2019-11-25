package org.w3c.dom

import org.w3c.geolocation.Geolocation
import org.w3c.indexeddb.IDBFactory

// TODO Diese Datei entfernen, sobald die Standard-Library von Kotlin diese Properties standardmäßig zur Verfügung stellt.

/**
 * Erweitert das globale [Window]-Objekt um das Property `indexedDB`. Die eigentliche Implementierung dieses Propertys erfolgt allerdings durch den
 * Webbrowser.
 *
 * @author Florian Steitz (fst)
 */
external val indexedDB: IDBFactory

/**
 * Erweitert das globale [Navigator]-Objekt um das Property `geolocation`. Das konkrete [Geolocation]-Objekt wird allerdings vom Webbrowser zur
 * Verfügung gestellt.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
val Navigator.geolocation: Geolocation
    get() = js("navigator.geolocation")