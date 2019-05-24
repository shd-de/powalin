package de.shd.basis.kotlin.ui.mvc.controller

import de.shd.basis.kotlin.ui.mvc.view.MVCView
import de.shd.basis.kotlin.ui.state.AppStateManager
import de.shd.basis.kotlin.ui.state.AppStateManager.openAsNewState
import kotlin.reflect.KClass

/**
 * Die abstrakte Standard-Implementierung von [MVCController], die u.a. die Verwaltung und Initialisierung der zugehörigen [MVCView] übernimmt.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class AbstractMVCController<VIEW : MVCView> : MVCController<VIEW> {

    final override val view: VIEW by lazy {
        createView()
    }

    final override fun init() {
        view.init()
        afterInit()
    }

    /**
     * Erzeugt eine neue Instanz von der spezifizierten Implementierung von [MVCController] via [MVCControllerFactory.create].
     */
    protected fun <CONTROLLER : MVCController<*>> createController(controllerClass: KClass<CONTROLLER>): CONTROLLER {
        return MVCControllerFactory.create(controllerClass)
    }

    /**
     * Erzeugt via [AppStateManager.openState] eine neue "Zustands-Instanz" für den spezifizierten [Controller][MVCController] oder stellt eine
     * bereits existierende "Zustands-Instanz" wiederher, je nachdem ob eine solche Instanz bereits im internen Cache des Frameworks vorhanden ist.
     *
     * Anschließend wird der Wurzelknoten der aktuellen Ansicht aus dem DOM-Baum des Frameworks ausgehängt und der Wurzelknoten der View, die von der
     * Instanz des spezifizierten Controllers verwaltet wird, stattdessen in den DOM-Baum des Frameworks eingehängt. Damit wird im Prinzip die
     * aktuelle Ansicht durch eine neue Ansicht ersetzt.
     *
     * Der Zustand der aktuellen Ansicht verbleibt allerdings im internen Cache des Frameworks. D.h. dieser Zustand kann jederzeit wiederhergestellt
     * werden, sofern er noch nicht durch die Anwendung explizit verworfen oder die Anwendung geschlossen wurde.
     *
     * Falls diese Methode eine neue "Zustands-Instanz" des spezifizierten Controllers erzeugt, wird sie zum internen Cache des Frameworks hinzugefügt.
     *
     * @see [openAsNewState]
     */
    protected fun <CONTROLLER : MVCController<*>> openState(controllerClass: KClass<CONTROLLER>): CONTROLLER {
        return AppStateManager.openState(controllerClass)
    }

    /**
     * Erzeugt via [AppStateManager.openAsNewState] eine neue, frische "Zustands-Instanz" für den spezifizierten [Controller][MVCController] und
     * verwirft eine möglicherweise bereits im internen Cache des Frameworks vorhandene Instanz unwiderruflich. D.h. die möglicherweise bereits
     * vorhandene "Zustands-Instanz" wird durch die neue Instanz ersetzt.
     *
     * Anschließend wird der Wurzelknoten der aktuellen Ansicht aus dem DOM-Baum des Frameworks ausgehängt und der Wurzelknoten der View, die von der
     * Instanz des spezifizierten Controllers verwaltet wird, stattdessen in den DOM-Baum des Frameworks eingehängt. Damit wird im Prinzip die
     * aktuelle Ansicht durch eine neue Ansicht ersetzt.
     *
     * Der Zustand der aktuellen Ansicht verbleibt allerdings im internen Cache des Frameworks, sofern diese Ansicht nicht zum spezifizierten
     * Controller gehört. D.h. der aktuelle Zustand kann jederzeit wiederhergestellt werden, sofern er noch nicht durch die Anwendung oder durch die
     * Methode [AppStateManager.openAsNewState] explizit verworfen und die Anwendung auch noch nicht geschlossen wurde.
     */
    protected fun <CONTROLLER : MVCController<*>> openAsNewState(controllerClass: KClass<CONTROLLER>): CONTROLLER {
        return AppStateManager.openAsNewState(controllerClass)
    }

    /**
     * Hängt via [AppStateManager.openDefaultState] den Wurzelknoten der aktuellen Ansicht aus dem DOM-Baum des Frameworks und hängt den Wurzelknoten
     * der View, die vom Controller der Standard-Ansicht verwaltet wird, stattdessen in den DOM-Baum des Frameworks. Damit wird im Prinzip die
     * aktuelle Ansicht durch die Standard-Ansicht ersetzt.
     *
     * Der Zustand der aktuellen Ansicht verbleibt allerdings im internen Cache des Frameworks. D.h. dieser Zustand kann jederzeit wiederhergestellt
     * werden, sofern er noch nicht durch die Anwendung explizit verworfen oder die Anwendung geschlossen wurde.
     */
    protected fun openDefaultState() {
        AppStateManager.openDefaultState()
    }

    /**
     * Gibt die vom Framework derzeit verwaltete "Zustands-Instanz" des spezifizierten [Controllers][MVCController] via [AppStateManager.getState]
     * zurück. Falls das Framework noch keine solche Instanz verwaltet, wird `null` zurückgegeben.
     */
    protected fun <CONTROLLER : MVCController<*>> getState(controllerClass: KClass<CONTROLLER>): CONTROLLER? {
        return AppStateManager.getState(controllerClass)
    }

    /**
     * Ableitende Controller können diese Methode optional überschreiben, um Logiken auszuführen, die nach der Initialisierung der zugehörigen
     * [MVCView] ausgeführt werden sollen. Dazu gehört im Regelfall u.a. das Registrieren von Event-Listenern (wie z.B. Klick-Listener).
     */
    protected open fun afterInit() {
        // Standardmäßig tut diese Methode nichs.
    }

    /**
     * Instanziiert die View, die von diesem Controller verwaltet werden soll. Diese Methode soll niemals manuell aufgerufen werden, weil sich diese
     * abstrakte Implementierung von [MVCController] (und damit das Framework) darum kümmert. Außerdem soll ein Controller stets exakt eine Instanz
     * seiner View halten und diese Instanz niemals austauschen.
     */
    protected abstract fun createView(): VIEW
}