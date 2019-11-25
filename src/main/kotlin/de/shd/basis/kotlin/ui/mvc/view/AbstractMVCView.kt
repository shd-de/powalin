package de.shd.basis.kotlin.ui.mvc.view

import de.shd.basis.kotlin.ui.component.AbstractUIComponent
import de.shd.basis.kotlin.ui.component.SHDUIComponent
import de.shd.basis.kotlin.ui.util.function.withSizeFull
import kotlinx.html.js.div

/**
 * Die abstrakte Standard-Implementierung von [MVCView], von der jede View einer MVC-Komponente erben soll. Sie definiert ein `div`-Element als
 * [Wurzelknoten][SHDUIComponent.rootNode].
 *
 * @author Florian Steitz (fst)
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class AbstractMVCView : AbstractUIComponent<MVCView>(), MVCView {

    // Eine View soll immer ein "div" als Wurzelknoten haben. Und eine ableitende View soll dies auch nicht überschreiben können. Darüber hinaus soll
    // dieses "div" standardmäßig auch die maximal verfügbare Höhe und Breite einnehmen, da das Framework vor allem für Mobile-First-Anwendungen
    // gedacht ist.
    final override val rootNode = nodeFactory.div().withSizeFull()
}