@file:Suppress("unused")

package de.shd.basis.kotlin.ui.app

import de.shd.basis.kotlin.ui.mvc.controller.MVCController
import de.shd.basis.kotlin.ui.util.appendScript
import de.shd.basis.kotlin.ui.util.appendScripts
import de.shd.basis.kotlin.ui.util.appendStylesheet
import de.shd.basis.kotlin.ui.util.appendStylesheets
import kotlin.browser.document
import kotlin.browser.window

/**
 * Diese Klasse ist der zentrale Einstiegspunkt des Kotlin- und MVC-basierten Basis-Frameworks für Single Page Applications (SPA).
 *
 * Im Regelfall wird sie in der `main`-Methode einer Anwendung instanziiert. An den Standard-Konstruktor muss der Anwendungstitel und der Controller
 * der Wurzel-Komponente der Anwendung übergeben werden.
 *
 * Anschließend kann über die API dieser Klasse das Framework konfiguriert und bei Bedarf zusätzliche JavaScript und CSS-Dateien zur zugrundeliegenden
 * `index.html` hinzugefügt werden.
 *
 * Sobald alle anwendungsspezifischen Standard-Konfigurationen durchgeführt wurden, kann das Framework (und damit die Anwendung) via [run] gestartet
 * werden. Diese Methode kümmert sich dann um das vollständige initialisieren des Frameworks und des DOMS. Nach Aufruf dieser Methode ist es nicht
 * mehr möglich, weitere Konfigurationen durchzuführen.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SHDApp(private val appTitle: String, private val appRootCtrl: MVCController<*>) {

    private val customScripts = mutableListOf<String>()
    private val customStylesheets = mutableListOf<String>()

    /**
     * Importiert die spezifizierte JavaScript-Datei, indem via [appendScript] ein zusätzliches `script`-Element zum DOM hinzugefügt wird.
     */
    fun withCustomScript(scriptURL: String): SHDApp {
        customScripts.add(scriptURL)
        return this
    }

    /**
     * Importiert die spezifizierte CSS-Datei, indem via [appendStylesheet] ein zusätzliches `link`-Element zum DOM hinzugefügt wird.
     */
    fun withCustomStylesheet(stylesheetURL: String): SHDApp {
        customStylesheets.add(stylesheetURL)
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

        // Den DOM-Subtree der Anwendung in den DOM-Tree des Frameworks integrieren, sobald der Webbrowser die Datei "index.html" vollständig geladen
        // und geparst hat. Erst danach kann via JavaScript zuverlässig auf die Elemente des DOMs zugegriffen werden.
        window.addEventListener("DOMContentLoaded", {
            document.querySelector(".shd-app")?.appendChild(appRootCtrl.view.rootNode)
        });
    }
}