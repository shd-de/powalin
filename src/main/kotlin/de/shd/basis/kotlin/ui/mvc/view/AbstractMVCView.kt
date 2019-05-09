package de.shd.basis.kotlin.ui.mvc.view

import kotlinx.html.dom.create
import kotlinx.html.js.div
import kotlin.browser.document

/**
 * Die abstrakte Standard-Implementierung von [MVCView], von der jede View einer Komponente erben soll. Sie definiert ein `div`-Element als [rootNode].
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
abstract class AbstractMVCView : MVCView {
    final override val rootNode = document.create.div { }
}