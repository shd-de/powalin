package de.shd.basis.kotlin.ui.window

import de.shd.basis.kotlin.ui.component.window.SHDWindow
import de.shd.basis.kotlin.ui.mvc.controller.MVCControllerFactory

/**
 *
 * @author Florian Steitz (fst)
 */
object WindowManager {

    internal var windowCtrl = MVCControllerFactory.createDefaultWindowCtrl()

    internal fun showWindow(window: SHDWindow) {
        windowCtrl.showWindow(window)
    }

    internal fun closeWindow(window: SHDWindow) {
        windowCtrl.closeWindow(window)
    }
}