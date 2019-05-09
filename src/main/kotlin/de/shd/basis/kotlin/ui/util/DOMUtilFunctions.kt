package de.shd.basis.kotlin.ui.util

import kotlinx.html.TagConsumer
import kotlinx.html.dom.create
import kotlinx.html.link
import kotlinx.html.script
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import kotlin.browser.document

/**
 * Fügt ein zusätzliches `script`-Element` via [appendScript] für jede übergebene URL einer JavaScript-Datei zum aktuellen Dokument hinzu.
 *
 * @author Florian Steitz (fst)
 */
fun appendScripts(scriptURLs: Collection<String>) {
    scriptURLs.forEach { appendScript(it) }
}

/**
 * Fügt ein zusätzliches `script`-Element mit den Attributen `type=text/javascript` und `src` via [appendHeadNode] zum aktuellen Dokument hinzu. Die
 * spezifizierte URL einer JavaScript-Datei wird dabei als Wert von `src` gesetzt.
 *
 * @author Florian Steitz (fst)
 */
fun appendScript(scriptURL: String) {
    appendHeadNode {
        script {
            type = "text/javascript"
            src = scriptURL
        }
    }
}

/**
 * Fügt ein zusätzliches `link`-Element` via [appendStylesheet] für jede übergebene URL einer CSS-Datei zum aktuellen Dokument hinzu.
 *
 * @author Florian Steitz (fst)
 */
fun appendStylesheets(stylesheetURLs: Collection<String>) {
    stylesheetURLs.forEach { appendStylesheet(it) }
}

/**
 * Fügt ein zusätzliches `link`-Element mit den Attributen `rel=stylesheet`, `type=text/css` und `href` via [appendHeadNode] zum aktuellen Dokument
 * hinzu. Die spezifizierte URL einer CSS-Datei wird dabei als Wert von `href` gesetzt.
 *
 * @author Florian Steitz (fst)
 */
fun appendStylesheet(stylesheetURL: String) {
    appendHeadNode {
        link {
            rel = "stylesheet"
            type = "text/css"
            href = stylesheetURL
        }
    }
}

/**
 * Erzeugt einen [Node] via der übergebenen Funktion und fügt ihn als Kind zum `head`-Element des aktuellen Dokuments hinzu. Die übergebene Funktion
 * erhält einen DOM-Builder als Receiver, wodurch Nodes direkt innerhalb der Funktion deklariert werden können.
 *
 * @receiver Der Rückgabewert von [kotlinx.html.dom.create].
 * @author Florian Steitz (fst)
 */
fun appendHeadNode(nodeCreator: TagConsumer<HTMLElement>.() -> Node) {
    document.head?.appendChild(nodeCreator(document.create))
}