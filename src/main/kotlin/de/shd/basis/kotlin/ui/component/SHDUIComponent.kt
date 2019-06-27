package de.shd.basis.kotlin.ui.component

import de.shd.basis.kotlin.ui.mvc.controller.MVCController
import de.shd.basis.kotlin.ui.mvc.view.MVCView
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import org.w3c.dom.events.Event

/**
 * Ein Interface, das von allen UI-Komponenten implementiert werden soll, die kein Ableitung von [Node] sind.
 *
 * Jede UI-Komponente hat stets genau einen Wurzelknoten, den Implementierungen von diesem Interface festlegen müssen. Dabei kann jede beliebige
 * Ableitung von [HTMLElement] als Wurzelknoten verwendet werden. Komplexe bzw. aus mehreren Elementen bestehende Komponenten sollen aber im Regelfall
 * [HTMLDivElement] als Wurzelknoten verwenden.
 *
 * Implementierungen von diesem Interface sollen ausschließlich von anderen Implementierungen dieses Interfaces erzeugt und verwaltet werden. Dazu
 * gehören auch Implementierungen von [MVCView]. Konkret heißt das, dass weder Implementierungen von [MVCController] noch andere Klassen, die nichts
 * mit der Darstellungsschicht zu tun haben, jemals mit Implementierungen von diesem Interface direkt arbeiten sollen.
 *
 * @author Florian Steitz (fst)
 */
interface SHDUIComponent {

    /**
     * Der Wurzelknoten dieser UI-Komponente, der zu anderen UI-Komponenten als Kind-Knoten hinzugefügt wird. Alle Komponenten-spezifischen Knoten
     * müssen sich dabei unterhalb dieses Wurzelknotens befinden.
     */
    val rootNode: HTMLElement

    /**
     * Fügt einen Event-Listener zu dieser UI-Komponente hinzu, der ausgeführt wird, wenn ein Anwender auf den Wurzelknoten dieser UI-Komponente
     * klickt.
     *
     * @see de.shd.basis.kotlin.ui.util.function.addClickListener
     */
    fun addClickListener(clickListener: () -> Unit)

    /**
     * Fügt einen Event-Listener zu dieser UI-Komponente hinzu, der ausgeführt wird, wenn ein Anwender auf den Wurzelknoten dieser UI-Komponente
     * klickt.
     *
     * @see de.shd.basis.kotlin.ui.util.function.addClickListener
     */
    fun addClickListener(clickListener: (Event) -> Unit)
}
