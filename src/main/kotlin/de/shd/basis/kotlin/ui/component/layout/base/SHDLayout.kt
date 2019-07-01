package de.shd.basis.kotlin.ui.component.layout.base

import de.shd.basis.kotlin.ui.component.SHDFluentUIComponent
import de.shd.basis.kotlin.ui.component.SHDUIComponent
import de.shd.basis.kotlin.ui.util.function.appendChild
import de.shd.basis.kotlin.ui.util.function.prependChild
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node

/**
 * Ein Interface, das von allen UI-Komponenten implementiert werden soll, die nur dazu dienen, Kindelemente bzw. Kindkomponenten einheitlich zu
 * positionieren und zu orientieren. Solche Komponenten werden "Layout-Komponenten" genannt.
 *
 * Jede Layout-Komponente hat stets genau einen Wurzelknoten, den Implementierungen von diesem Interface festlegen müssen. Dabei kann jede beliebige
 * Ableitung von [HTMLElement] als Wurzelknoten verwendet werden. Im Regelfall soll aber das [HTMLDivElement] als Wurzelknoten verwendet werden.
 *
 * Der zu spezifizierende, generische Parameter dient nur dazu, Fluent-APIs zu ermöglichen. D.h. Implementierungen von diesem Interface müssen sich
 * selbst als Parameter angeben.
 *
 * @author Florian Steitz (fst)
 */
interface SHDLayout<LAYOUT : SHDLayout<LAYOUT>> : SHDFluentUIComponent<LAYOUT> {

    /**
     * Fügt das übergebene [HTML-Element][HTMLElement] via [Node.appendChild] an das Ende der Liste der Kindknoten dieser Layout-Komponente hinzu.
     */
    fun add(element: HTMLElement): LAYOUT

    /**
     * Fügt das übergebene [HTML-Element][HTMLElement] via [prependChild] an den Anfang der Liste der Kindknoten dieser Layout-Komponente hinzu.
     */
    fun addAsFirst(element: HTMLElement): LAYOUT

    /**
     * Fügt die übergebenen [HTML-Elemente][HTMLElement] via [Node.appendChild] an das Ende der Liste der Kindknoten dieser Layout-Komponente in der
     * übergebenen Reihenfolge hinzu.
     */
    fun addAll(vararg elements: HTMLElement): LAYOUT

    /**
     * Entfernt das übergebene [HTML-Element][HTMLElement] via [Node.removeChild] aus der Liste der Kindknoten dieser Layout-Komponente.
     */
    fun remove(element: HTMLElement): LAYOUT

    /**
     * Fügt die übergebene [UI-Komponente][SHDUIComponent] via [appendChild] an das Ende der Liste der Kindknoten dieser Layout-Komponente hinzu.
     */
    fun add(component: SHDUIComponent): LAYOUT {
        return add(component.rootNode)
    }

    /**
     * Fügt die übergebene [UI-Komponente][SHDUIComponent] via [prependChild] an den Anfang der Liste der Kindknoten dieser Layout-Komponente hinzu.
     */
    fun addAsFirst(component: SHDUIComponent): LAYOUT {
        return addAsFirst(component.rootNode)
    }

    /**
     * Entfernt die übergebene [UI-Komponente][SHDUIComponent] via [Node.removeChild] aus der Liste der Kindknoten dieser Layout-Komponente.
     */
    fun remove(component: SHDUIComponent): LAYOUT {
        return remove(component.rootNode)
    }

    /**
     * Entfernt alle Kindelemente bzw. Kindkomponenten aus dieser Layout-Komponente.
     */
    fun clear(): LAYOUT

    /**
     * Fügt die übergebenen [UI-Komponenten][SHDUIComponent] via [appendChild] an das Ende der Liste der Kindknoten dieser Layout-Komponente in der
     * übergebenen Reihenfolge hinzu.
     */
    @Suppress("UNCHECKED_CAST")
    fun addAll(vararg components: SHDUIComponent): LAYOUT {
        components.forEach { add(it.rootNode) }
        return this as LAYOUT
    }
}