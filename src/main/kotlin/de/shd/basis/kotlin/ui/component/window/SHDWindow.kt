package de.shd.basis.kotlin.ui.component.window

import de.shd.basis.kotlin.ui.component.SHDUIComponent
import de.shd.basis.kotlin.ui.window.WindowManager
import org.w3c.dom.HTMLElement

/**
 *
 * @author Florian Steitz (fst)
 */
interface SHDWindow : SHDUIComponent {

    fun close() {
        WindowManager.closeWindow(this)
    }

    /**
     * Ruft intern die Methode [HTMLElement.focus] des Wurzelknotens von diesem Overlay-Fenster auf, wodurch dieses Fenster fokussiert wird, sofern es
     * denn fokussiert werden kann.
     */
    fun focus(): SHDWindow {
        rootNode.focus()
        return this
    }
}