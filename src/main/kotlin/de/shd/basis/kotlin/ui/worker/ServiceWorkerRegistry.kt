package de.shd.basis.kotlin.ui.worker

import de.shd.basis.kotlin.ui.app.SHDApp
import org.w3c.workers.RegistrationOptions
import org.w3c.workers.ServiceWorkerContainer
import org.w3c.workers.ServiceWorkerRegistration
import kotlin.browser.window
import kotlin.js.Promise

/**
 * Die zentrale Registry zum Registrieren sog. [ServiceWorker](https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API). Bei einem
 * `ServiceWorker` handelt es sich (im Regelfall) um eine JavaScript-Datei, die im Webbrowser über einer URL "installiert" und anschließend isoliert
 * ausgeführt wird.
 *
 * Dieses Framework stellt bspw. eine API für `ServiceWorker` zur Verfügung, über die eine Anwendung offlinefähig gemacht werden kann, da solche
 * `ServiceWorker` über diese API u.a. alle statischen Ressourcen dieses Frameworks standardmäßig und dauerhaft im Webbrowser cachen können.
 *
 * Die API dieser Registry soll aber nur in Ausnahmefällen direkt aufgerufen werden. Im Regelfall sollen `ServiceWorker` über die API von [SHDApp]
 * registriert werden.
 *
 * @author Florian Steitz (fst)
 */
object ServiceWorkerRegistry {

    // Fungiert nur als Convenience-Alias von [Navigator.serviceWorker].
    private val serviceWorkerContainer = window.navigator.serviceWorker

    /**
     * Registriert den [ServiceWorker](https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API), auf den die übergebene URL zeigt, mit dem
     * Standard-Scope via [ServiceWorkerContainer.register]. Der Standard-Scope entspricht dem Pfad, in dem der `ServiceWorker` liegt, inklusive allen
     * Unterpfaden.
     */
    fun register(scriptURL: String) {
        handleRegistrationResult(serviceWorkerContainer.register(scriptURL), scriptURL)
    }

    /**
     * Registriert den [ServiceWorker](https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API), auf den die übergebene URL zeigt, mit dem
     * übergebenen Scope via [ServiceWorkerContainer.register]. Der übergebene Scope muss einem Pfad entsprechen, in dem der `ServiceWorker` liegt,
     * oder einem Unterpfad davon.
     */
    fun register(scriptURL: String, scope: String) {
        handleRegistrationResult(serviceWorkerContainer.register(scriptURL, createRegistrationOptions(scope)), scriptURL)
    }

    /**
     * Schreibt entweder eine Info- oder eine Error-Meldung in die Webbrowser-Konsole, je nach Ergebnis des übergebenen [Promises][Promise]. Die
     * übergebene Skript-URL wird aber in jedem Fall in die Webbrowser-Konsole geschrieben.
     */
    private fun handleRegistrationResult(workerPromise: Promise<ServiceWorkerRegistration>, scriptURL: String) {
        workerPromise
                .then { console.info("ServiceWorker '$scriptURL' wurde erfolgreich registriert") }
                .catch { console.error("ServiceWorker '$scriptURL' konnte nicht registriert werden: ${it.message}") }
    }

    /**
     * Erstellt eine (anonyme) Implementierung von [RegistrationOptions], die nur den übergebenen Scope enthält.
     */
    private fun createRegistrationOptions(scope: String): RegistrationOptions {
        return object : RegistrationOptions {
            override var scope: String?
                get() = scope
                set(_) {}
        }
    }
}