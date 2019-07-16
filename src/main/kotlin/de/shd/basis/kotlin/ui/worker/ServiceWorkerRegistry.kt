package de.shd.basis.kotlin.ui.worker

import de.shd.basis.kotlin.ui.app.SHDApp
import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import de.shd.basis.kotlin.ui.util.function.clearCacheStorage
import org.w3c.workers.RegistrationOptions
import org.w3c.workers.ServiceWorkerContainer
import org.w3c.workers.ServiceWorkerRegistration
import kotlin.browser.window
import kotlin.js.Promise

/**
 * Die zentrale Registry zum Registrieren und Verwalten sog. [ServiceWorker](https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API). Bei
 * einem `ServiceWorker` handelt es sich (im Regelfall) um eine JavaScript-Datei, die im Webbrowser über einer URL "installiert" und anschließend
 * isoliert ausgeführt wird.
 *
 * Dieses Framework stellt bspw. eine API für `ServiceWorker` zur Verfügung, über die eine Anwendung offlinefähig gemacht werden kann, indem über
 * diese API u.a. alle statischen Ressourcen dieses Frameworks standardmäßig und dauerhaft im Webbrowser gecacht werden können. Solche Caches können
 * anschließend bei Bedarf via [clearCacheStorage] wieder geleert werden.
 *
 * Die API dieser Registry soll aber nur in Ausnahmefällen direkt aufgerufen werden (bspw. um einen bereits registrierten `ServiceWorker` wieder zu
 * deinstallieren). Wenn `ServiceWorker` nur registriert werden müssen, soll dies aber im Regelfall primär über die API von [SHDApp] geschehen.
 *
 * @author Florian Steitz (fst)
 */
object ServiceWorkerRegistry {

    // Fungiert nur als Convenience-Alias von [Navigator.serviceWorker].
    private val serviceWorkerContainer = window.navigator.serviceWorker

    // Interner Cache, der das Registrierungsobjekt jedes registrierten ServiceWorker-Skripts vorhält. Wird u.a. benötigt, um ServiceWorker bei
    // Bedarf später wieder deinstallieren zu können.
    private val registrationByScriptMap = mutableMapOf<String, ServiceWorkerRegistration>()

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
     * Deinstalliert den [ServiceWorker](https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API), auf den die übergebene URL zeigt und
     * gibt das Ergebnis von [ServiceWorkerContainer.unregister] zurück. Falls er noch nicht registriert bzw. installiert wurde, wird eine
     * [SHDRuntimeException] geworfen.
     */
    @Suppress("unused")
    fun unregister(scriptURL: String): Promise<Boolean> {
        val registration = registrationByScriptMap[scriptURL] ?: throw SHDRuntimeException("ServiceWorker '$scriptURL' wurde nicht registriert")
        return registration.unregister()
    }

    /**
     * Im Erfolgsfall cacht diese Methode das [Ergebnisobjekt][ServiceWorkerRegistration] des übergebenen [Promises][Promise] und schreibt eine
     * entsprechende Info-Meldung in die Webbrowser-Konsole. Im Fehlerfall wird nur eine Error-Meldung in die Webbrowser-Konsole geschrieben.
     * Unabhängig vom Ergebnis wird aber in beiden Fällen die übergebene Skript-URL in die Webbrowser-Konsole geschrieben.
     */
    private fun handleRegistrationResult(workerPromise: Promise<ServiceWorkerRegistration>, scriptURL: String) {
        workerPromise
                .then { registration -> handleSuccessfulRegistration(registration, scriptURL) }
                .catch { console.error("ServiceWorker '$scriptURL' konnte nicht registriert werden: ${it.message}") }
    }

    /**
     * Cacht das übergebene [Registrierungsobjekt][ServiceWorkerRegistration] für das spezifizierte `ServiceWorker`-Skript und schreibt eine
     * entsprechende Info-Meldung in die Webbrowser-Konsole.
     */
    private fun handleSuccessfulRegistration(registration: ServiceWorkerRegistration, scriptURL: String) {
        registrationByScriptMap[scriptURL] = registration
        console.info("ServiceWorker '$scriptURL' wurde erfolgreich registriert");
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