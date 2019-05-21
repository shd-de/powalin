@file:Suppress("unused")

package de.shd.basis.kotlin.ui.app

import de.shd.basis.kotlin.ui.component.menu.app.AppMenuController
import de.shd.basis.kotlin.ui.i18n.I18n
import de.shd.basis.kotlin.ui.i18n.I18nMessageProvider
import de.shd.basis.kotlin.ui.mvc.controller.MVCController
import de.shd.basis.kotlin.ui.mvc.controller.MVCControllerFactory
import de.shd.basis.kotlin.ui.util.appendScript
import de.shd.basis.kotlin.ui.util.appendScripts
import de.shd.basis.kotlin.ui.util.appendStylesheet
import de.shd.basis.kotlin.ui.util.appendStylesheets
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.reflect.KClass

/**
 * Diese Klasse ist der zentrale Einstiegspunkt des auf Kotlin und dem MVC-Muster basierenden Basis-Frameworks für offlinefähige Single Page
 * Applications (SPA).
 *
 * Im Regelfall wird sie in der `main`-Methode einer Anwendung instanziiert. An den Standard-Konstruktor muss der Anwendungstitel der Anwendung
 * übergeben werden, der im Fenster bzw. Tab des Webbrowsers angezeigt wird.
 *
 * Anschließend kann über die API dieser Klasse das Framework konfiguriert und so u.a. bei Bedarf zusätzliche JavaScript und CSS-Dateien zur
 * zugrundeliegenden `index.html` hinzugefügt werden. Auch kann über die API festgelegt werden, welche MVC-Komponente standardmäßig bzw. initial
 * angezeigt werden soll. Wenn keine initial anzuzeigende MVC-Komponente von einer Anwendung explizit festgelegt wird, wird standardmäßig das zentrale,
 * konfigurierbare Anwendungsmenü des Frameworks angezeigt (siehe [AppMenuController]).
 *
 * Sobald alle anwendungsspezifischen Standard-Konfigurationen durchgeführt wurden, kann das Framework (und damit die Anwendung) via [run] gestartet
 * werden. Diese Methode kümmert sich anschließend um das vollständige initialisieren des Frameworks und des (initialen) DOMS. Nach Aufruf dieser
 * Methode ist es nicht mehr möglich, weitere Konfigurationen über die API dieser Klasse durchzuführen.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SHDApp(private val appTitle: String) {

    private val appMenuCtrl = MVCControllerFactory.createAppMenuCtrl()
    private var defaultRootCtrl: MVCController<*> = appMenuCtrl // Standardmäßig das zentrale Anwendungsmenü anzeigen.

    private val customScripts = mutableListOf<String>()
    private val customStylesheets = mutableListOf<String>()

    /**
     * Legt anhand der übergebenen Klasse fest, welche MVC-Komponente standardmäßig bzw. initial angezeigt werden soll. Das Framework merkt sich diese
     * Komponente und daher kann stets zu ihr wieder "navigiert" werden. Denn das Framework instanziiert den spezifizierten [MVCController] selbst und
     * hält sich diese Instanz als Singleton permanent vor.
     */
    fun <CONTROLLER : MVCController<*>> withDefaultRootComponent(controllerClass: KClass<CONTROLLER>): SHDApp {
        return withDefaultRootComponent(MVCControllerFactory.create(controllerClass))
    }

    /**
     * Legt anhand des übergebenen [MVCControllers][MVCController] fest, welche MVC-Komponente standardmäßig bzw. initial angezeigt werden soll. Das
     * Framework merkt sich diese Komponente und daher kann stets zu ihr wieder "navigiert" werden.
     */
    fun withDefaultRootComponent(controller: MVCController<*>): SHDApp {
        defaultRootCtrl = controller
        return this
    }

    /**
     * Importiert die spezifizierten JavaScript-Dateien, indem via [appendScript] ein zusätzliches `script`-Element pro übergebener Datei-URL zum DOM
     * hinzugefügt wird.
     */
    fun withScripts(vararg scriptURLs: String): SHDApp {
        customScripts.addAll(scriptURLs)
        return this
    }

    /**
     * Importiert die spezifizierten CSS-Dateien, indem via [appendStylesheet] ein zusätzliches `link`-Element pro übergebener Datei-URL zum DOM
     * hinzugefügt wird.
     */
    fun withStylesheets(vararg stylesheetURLs: String): SHDApp {
        customStylesheets.addAll(stylesheetURLs)
        return this
    }

    /**
     * Legt die Implementierung von [I18nMessageProvider] fest, die von [I18n.getText] zum Auflösen von Übersetzung auf Basis von I18n-Schlüsseln
     * verwendet werden soll.
     */
    fun withI18nMessageProvider(messageProvider: I18nMessageProvider): SHDApp {
        I18n.messageProvider = messageProvider
        return this
    }

    /**
     * Legt die aktuelle Sprache der Anwendung anhand eines Sprach-Codes fest.
     *
     * Das Format, das Sprach-Codes haben müssen, ist durch [BCP 47](https://tools.ietf.org/rfc/bcp/bcp47.txt) festgelegt. Zum Beispiel sind die
     * folgenden Sprach-Code-Formate valide:
     * - en
     * - en-US
     * - de
     * - de-DE
     *
     * @see I18n.currentLanguage
     */
    fun withLanguage(language: String): SHDApp {
        I18n.currentLanguage = language
        return this
    }

    /**
     * Ermöglicht die Konfiguration des zentralen Anwendungsmenüs des Frameworks über die übergebene Funktion, indem der intern vorgehaltene
     * [AppMenuController] als Receiver an die übergebene Funktion übergeben wird.
     */
    fun configureAppMenu(configurator: AppMenuController.() -> Unit): SHDApp {
        configurator(appMenuCtrl)
        return this
    }

    /**
     * Startet die Anwendung, indem das Basis-Framework initialisiert wird, welches widerum die eigentliche Anwendung initialisiert.
     */
    fun run() {
        // Anwendungsspezifische Ressourcen zum DOM hinzufügen.
        appendScripts(customScripts)
        appendStylesheets(customStylesheets)

        // Den Titel des aktuellen HTML-Dokuments festlegen. Dieser wird dann im Fenster bzw. Tab des Webbrowsers angezeigt und fungiert damit als
        // "Anwendungstitel".
        document.title = appTitle

        // Die grundlegende DOM-Struktur für die Anwendung initialisieren, sobald der Webbrowser die Datei "index.html" vollständig geladen und
        // geparst hat. Erst danach kann via JavaScript zuverlässig auf die (bereits vorhandenen) Elemente des DOMs zugegriffen werden.
        window.addEventListener("DOMContentLoaded", { updateDOM() })
    }

    /**
     * Initialisiert die grundlegende DOM-Struktur für die Anwendung und überschreibt bei Bedarf auch die Attribute-Werte von bereits vorhandenen
     * Elementen.
     */
    private fun updateDOM() {
        // Den DOM-Subtree der (initialen) Standard-Komponente in den DOM-Tree des Frameworks integrieren.
        document.querySelector(".shd-app")?.appendChild(defaultRootCtrl.view.rootNode)

        // Die (deklarative) Sprache des Dokuments aktualisieren, damit sie auch zur tatsächlich verwendeten bzw. angezeigten Sprache passt.
        (document.documentElement as HTMLElement).lang = I18n.currentLanguage
    }
}