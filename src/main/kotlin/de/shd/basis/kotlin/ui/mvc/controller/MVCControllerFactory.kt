package de.shd.basis.kotlin.ui.mvc.controller

import de.shd.basis.kotlin.ui.component.menu.app.AppMenuCtrl
import de.shd.basis.kotlin.ui.component.window.DefaultWindowCtrl
import de.shd.basis.kotlin.ui.mvc.controller.MVCControllerFactory.create
import de.shd.basis.kotlin.ui.util.function.createInstance
import kotlin.reflect.KClass

/**
 * Zentrale Singleton-Factory zum Instanziieren von Implementierungen von [MVCController].
 *
 * Zusätzlich zur Instanziierung von MVC-Controllern übernimmt diese Factory auch die Initialisierung dieser via [MVCController.init]. D.h. ein
 * Aufrufer der Methode [create] kann sich darauf verlassen, dass der zurückgegebene MVC-Controller vollständig initialisiert ist.
 *
 * @author Florian Steitz (fst)
 */
object MVCControllerFactory {

    /**
     * Erzeugt eine neue Instanz von der spezifizierten Implementierung von [MVCController] und initialisiert sie via [MVCController.init], bevor sie
     * zurückgegeben wird.
     */
    fun <CONTROLLER : MVCController<*>> create(controllerClass: KClass<CONTROLLER>): CONTROLLER {
        val ctrl = createInstance(controllerClass)
        ctrl.init()

        return ctrl
    }

    /**
     * Erzeugt eine neue Instanz von [AppMenuCtrl] via [create].
     */
    internal fun createAppMenuCtrl(): AppMenuCtrl {
        return create(AppMenuCtrl::class)
    }

    /**
     * Erzeugt eine neue Instanz von [DefaultWindowCtrl] via [create].
     */
    internal fun createDefaultWindowCtrl(): DefaultWindowCtrl {
        return create(DefaultWindowCtrl::class)
    }
}