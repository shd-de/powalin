package de.shd.basis.kotlin.ui.util

import de.shd.basis.kotlin.ui.mvc.view.MVCView
import org.w3c.dom.Node
import org.w3c.dom.events.EventTarget

/**
 * Fügt die übergebene [MVCView] via [Node.appendChild] an das Ende der Liste der Kindknoten dieses Elements hinzu.
 *
 * @see MVCView.rootNode
 * @author Florian Steitz (fst)
 */
fun Node.appendChild(view: MVCView) {
    appendChild(view.rootNode)
}

/**
 * Entfernt alle Kindknoten dieses Elements.
 *
 * @see Node.removeChild
 * @author Florian Steitz (fst)
 */
fun Node.removeAllChildren() {
    while (firstChild != null) {
        removeChild(firstChild as Node);
    }
}

/**
 * Fügt einen Event-Listener zu diesem Element hinzu, der ausgeführt wird, wenn ein Anwender auf dieses Element klickt.
 *
 * @see EventTarget.addEventListener
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun Node.addClickListener(listener: () -> Unit) {
    addEventListener("click", { listener() })
}