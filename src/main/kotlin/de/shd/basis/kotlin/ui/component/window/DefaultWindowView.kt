package de.shd.basis.kotlin.ui.component.window

import de.shd.basis.kotlin.ui.mvc.view.AbstractMVCView
import de.shd.basis.kotlin.ui.theme.BasisTheme
import de.shd.basis.kotlin.ui.util.function.removeAllChildren

/**
 *
 * @author Florian Steitz (fst)
 */
class DefaultWindowView : AbstractMVCView() {

    override fun init() {
        withStyleName(BasisTheme.CLASS_WINDOW)
        withVisible(false)
    }

    internal fun replaceWindow(window: SHDWindow) {
        if (rootNode.childNodes.length > 0) {
            rootNode.removeAllChildren()
        }

        rootNode.appendChild(window.rootNode)
    }

    internal fun removeWindow(window: SHDWindow) {
        rootNode.removeChild(window.rootNode)
    }
}