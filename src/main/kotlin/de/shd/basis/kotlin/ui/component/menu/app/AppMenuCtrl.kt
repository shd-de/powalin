package de.shd.basis.kotlin.ui.component.menu.app

import de.shd.basis.kotlin.ui.app.SHDApp
import de.shd.basis.kotlin.ui.mvc.controller.AbstractMVCController

/**
 * Die Framework-interne Standard-Implementierung von [AppMenuController], die nur über die API dieses Interfaces konfiguriert werden können soll.
 * Anwendungen können über die Methode [SHDApp.configureAppMenu] auf die API des Interfaces zugreifen.
 *
 * @author Florian Steitz (fst)
 */
internal class AppMenuCtrl : AbstractMVCController<AppMenuView>(), AppMenuController {

    override fun createView(): AppMenuView {
        return AppMenuView()
    }
}