package de.shd.basis.kotlin.ui.mvc.view

import de.shd.basis.kotlin.ui.util.function.withWidthFull
import kotlinx.html.dom.create
import kotlinx.html.js.div
import kotlin.browser.document

/**
 * Die abstrakte Standard-Implementierung von [MVCView], von der jede View einer Komponente erben soll. Sie definiert ein `div`-Element als [rootNode].
 *
 * @author Florian Steitz (fst)
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class AbstractMVCView : MVCView {

    // Ableitende Views sollen mit diesem Alias arbeiten, damit das Framework bei Bedarf eine eigene Factory für Knoten zur Verfügung stellen kann,
    // ohne dabei Migrationsaufwand zu verursachen.
    protected val nodeFactory = document.create

    // Eine View soll immer ein "div" als Wurzel-Knoten haben. Und eine ableitende View soll dies auch nicht überschreiben können. Darüber hinaus soll
    // dieses "div" standardmäßig auch die maximal verfügbare Breite einnehmen, da das Framework vor allem für Mobile-First-Anwendungen gedacht ist.
    final override val rootNode = nodeFactory.div().withWidthFull()
}