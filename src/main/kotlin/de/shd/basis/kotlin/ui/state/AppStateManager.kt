package de.shd.basis.kotlin.ui.state

import de.shd.basis.kotlin.ui.component.menu.app.AppMenuController
import de.shd.basis.kotlin.ui.mvc.controller.MVCController
import de.shd.basis.kotlin.ui.mvc.controller.MVCControllerFactory
import kotlin.reflect.KClass

/**
 * Zentraler Singleton-Manager, der alle bekannten "Zustände" der Anwendung kennt und verwaltet. Bei einem solchen "Zustand" handelt es sich um einen
 * [MVCController], der als Wurzelknoten einer alleinstehenden Ansicht in den DOM-Baum des Frameworks eingehängt werden kann. D.h. ein solcher
 * Controller verwaltet alle Zustandswerte (über Models), DOM-Strukturen (über Views) sowie Unter-Controller einer anwendungsspezifischen Ansicht.
 *
 * Dementsprechend kann die Anwendung über die API dieses Managers zwischen beliebigen Zuständen wechseln und neue erzeugen lassen. Dafür muss die
 * Anwendung stets die Klasse des [MVCControllers][MVCController] angeben, der als Wurzelknoten des neuen Zustands fungieren soll. Die Anwendung kann
 * allerdings keine Zustände eigenmächtig erzeugen, indem sie bspw. eine selbst erzeugte Controller-Instanz an diesen Manager übergibt. D.h.
 * ausschließlich dieser Manager kümmert sich um die Instanziierung sowie Verwaltung der Controller-Instanzen und kontrolliert damit deren
 * Lebenszyklus.
 *
 * Jede, von diesem Manager erzeugte, Instanz eines [Controllers][MVCController] wird dabei in einem internen Cache vorgehalten. D.h. Anwendungen können
 * jederzeit vorherige Zustände wiederherstellen. Allerdings hält dieser Manager nur genau eine Instanz pro Controller-Klasse vor. Daher ist es nicht
 * möglich, zwischen mehreren "Zustands-Instanzen" des gleichen Controllers zu wechseln. Allerdings kann eine bestehende "Zustands-Instanz" eines
 * Controllers durch eine neue, frische "Zustands-Instanz" unwiderruflich ersetzt werden. Sprich, es wird eine neue Instanz des Controllers erzeugt
 * und die alte Instanz wird verworfen. Diese neue Instanz ersetzt damit auch die alte Instanz im internen Cache dieses Managers.
 *
 * Die Anwendung muss über Aufrufe entsprechender Methoden dieses Managers selbst entscheiden, wann sie einen existierenden Zustand wiederherstellen
 * und wann sie eine neuen, frischen Zustand erzeugen möchte.
 *
 * @author Florian Steitz (fst)
 */
object AppStateManager {

    private val stateControllers = mutableMapOf<KClass<*>, MVCController<*>>()
    private val stateChangeListeners = mutableListOf<(MVCController<*>) -> Unit>()
    private val appMenuCtrl = MVCControllerFactory.createAppMenuCtrl()

    /**
     * Legt fest, welcher [Controller][MVCController] standardmäßig als Wurzelknoten verwendet (und damit als Standard-Ansicht angezeigt) werden soll.
     * Die Anwendung kann diesen Zustand jederzeit über die API dieses Managers wiederherstellen.
     *
     * Falls eine Anwendung keinen eigenen Standard-Controller festlegt, wird standardmäßig der [Controller][AppMenuController] des zentralen
     * Anwendungsmenüs verwendet.
     */
    internal var defaultStateController: MVCController<*> = appMenuCtrl

    /**
     * Erzeugt eine neue "Zustands-Instanz" für den spezifizierten [Controller][MVCController] oder stellt eine bereits existierende "Zustands-Instanz"
     * wiederher, je nachdem ob eine solche Instanz bereits im internen Cache dieses Managers vorhanden ist.
     *
     * Anschließend wird der Wurzelknoten der aktuellen Ansicht aus dem DOM-Baum des Frameworks ausgehängt und der Wurzelknoten der View, die von der
     * Instanz des spezifizierten Controllers verwaltet wird, stattdessen in den DOM-Baum des Frameworks eingehängt. Damit wird im Prinzip die
     * aktuelle Ansicht durch eine neue Ansicht ersetzt.
     *
     * Der Zustand der aktuellen Ansicht verbleibt allerdings im internen Cache dieses Managers. D.h. dieser Zustand kann jederzeit wiederhergestellt
     * werden, sofern er noch nicht durch die Anwendung explizit verworfen oder die Anwendung geschlossen wurde.
     *
     * Falls diese Methode eine neue "Zustands-Instanz" des spezifizierten Controllers erzeugt, wird sie zum internen Cache dieses Managers
     * hinzugefügt.
     *
     * @see [openAsNewState]
     */
    @Suppress("UNCHECKED_CAST")
    fun <CONTROLLER : MVCController<*>> openState(controllerClass: KClass<CONTROLLER>): CONTROLLER {
        return notifyListeners(stateControllers.getOrPut(controllerClass, { MVCControllerFactory.create(controllerClass) }) as CONTROLLER)
    }

    /**
     * Erzeugt eine neue, frische "Zustands-Instanz" für den spezifizierten [Controller][MVCController] und verwirft eine möglicherweise bereits im
     * internen Cache dieses Managers vorhandene Instanz unwiderruflich. D.h. die möglicherweise bereits vorhandene "Zustands-Instanz" wird durch die
     * neue Instanz ersetzt.
     *
     * Anschließend wird der Wurzelknoten der aktuellen Ansicht aus dem DOM-Baum des Frameworks ausgehängt und der Wurzelknoten der View, die von der
     * Instanz des spezifizierten Controllers verwaltet wird, stattdessen in den DOM-Baum des Frameworks eingehängt. Damit wird im Prinzip die
     * aktuelle Ansicht durch eine neue Ansicht ersetzt.
     *
     * Der Zustand der aktuellen Ansicht verbleibt allerdings im internen Cache dieses Managers, sofern diese Ansicht nicht zum spezifizierten
     * Controller gehört. D.h. der aktuelle Zustand kann jederzeit wiederhergestellt werden, sofern er noch nicht durch die Anwendung oder diese
     * Methode explizit verworfen und die Anwendung auch noch nicht geschlossen wurde.
     */
    fun <CONTROLLER : MVCController<*>> openAsNewState(controllerClass: KClass<CONTROLLER>): CONTROLLER {
        val ctrl = MVCControllerFactory.create(controllerClass)

        stateControllers[controllerClass] = ctrl
        notifyListeners(ctrl)

        return ctrl
    }

    /**
     * Gibt die von diesem Manager derzeit verwaltete "Zustands-Instanz" des spezifizierten [Controllers][MVCController] zurück. Falls dieser Manager
     * noch keine solche Instanz verwaltet, wird `null` zurückgegeben.
     */
    @Suppress("UNCHECKED_CAST")
    fun <CONTROLLER : MVCController<*>> getState(controllerClass: KClass<CONTROLLER>): CONTROLLER? {
        return stateControllers[controllerClass] as CONTROLLER?
    }

    /**
     * Ermöglicht die Konfiguration des zentralen Anwendungsmenüs über die übergebene Funktion, indem dessen [Controller][AppMenuController] als
     * Receiver an diese Funktion übergeben wird.
     */
    internal fun configureAppMenuState(configurator: AppMenuController.() -> Unit) {
        configurator(appMenuCtrl)
    }

    /**
     * Fügt einen Listener hinzu, der ausgeführt wird, wenn der Wurzelknoten der aktuellen Ansicht aus dem DOM-Baum des Frameworks ausgehängt und der
     * Wurzelknoten der View, die vom übergebenen Controller verwaltet wird, stattdessen in den DOM-Baum des Frameworks eingehängt werden soll.
     */
    internal fun addStateChangeListener(listener: (MVCController<*>) -> Unit) {
        stateChangeListeners.add(listener)
    }

    /**
     * Benachrichtigt alle Listener, die via [addStateChangeListener] registriert wurden, indem der übergebene Controller an die registrierte Funktion
     * übergeben wird.
     */
    private fun <CONTROLLER : MVCController<*>> notifyListeners(ctrl: CONTROLLER): CONTROLLER {
        stateChangeListeners.forEach { accept -> accept(ctrl) }
        return ctrl
    }
}