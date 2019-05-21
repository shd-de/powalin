package de.shd.basis.kotlin.ui.util

import de.shd.basis.kotlin.ui.mvc.view.MVCView
import org.w3c.dom.Node

/**
 * Fügt die übergebene [MVCView] via [Node.appendChild] an das Ende der Liste der Kindknoten dieses Elements hinzu.
 *
 * @see MVCView.rootNode
 * @author Florian Steitz (fst)
 */
fun Node.appendChild(view: MVCView) {
    appendChild(view.rootNode)
}