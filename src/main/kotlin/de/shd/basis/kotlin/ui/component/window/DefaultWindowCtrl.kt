package de.shd.basis.kotlin.ui.component.window

import de.shd.basis.kotlin.ui.mvc.controller.AbstractMVCController

/**
 *
 * @author Florian Steitz (fst)
 */
class DefaultWindowCtrl : AbstractMVCController<DefaultWindowView>() {

    fun showWindow(window: SHDWindow) {
        view.replaceWindow(window)
        view.withVisible(true)
        window.focus()
    }

    fun closeWindow(window: SHDWindow) {
        view.removeWindow(window)
        view.withVisible(false)
    }

    override fun createView(): DefaultWindowView {
        return DefaultWindowView()
    }
}