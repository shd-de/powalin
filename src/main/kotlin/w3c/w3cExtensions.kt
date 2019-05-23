package org.w3c.dom

import org.w3c.indexeddb.IDBFactory

// TODO Diese Datei entfernen, sobald die Standard-Library von Kotlin dieses Property standardmäßig zur Verfügung stellt.

/**
 * Erweitert das globale [Window]-Objekt um das Property `indexedDB`. Die eigentliche Implementierung dieses Propertys erfolgt allerdings durch den
 * Webbrowser.
 *
 * @author Florian Steitz (fst)
 */
external val indexedDB: IDBFactory