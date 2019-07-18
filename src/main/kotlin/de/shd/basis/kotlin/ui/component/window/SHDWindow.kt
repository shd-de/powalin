package de.shd.basis.kotlin.ui.component.window

import de.shd.basis.kotlin.ui.component.SHDUIComponent
import de.shd.basis.kotlin.ui.window.WindowManager

/**
 *
 * @author Florian Steitz (fst)
 */
interface SHDWindow : SHDUIComponent {

    fun close() {
        WindowManager.closeWindow(this)
    }
}