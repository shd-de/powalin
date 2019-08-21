package de.shd.basis.kotlin.ui.component.layout.base

import de.shd.basis.kotlin.ui.component.SHDUIComponent
import de.shd.basis.kotlin.ui.theme.BasisTheme
import de.shd.basis.kotlin.ui.util.function.prependChild
import de.shd.basis.kotlin.ui.util.function.withFullWidth
import de.shd.basis.kotlin.ui.util.function.withStyleName
import kotlinx.html.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node

/**
 * Die abstrakte Standard-Implementierung von [SHDFlexLayout], von der alle Layout-Komponenten erben sollen, die ihre Kindelemente bzw. ihre
 * Kindkomponenten primär mit CSS-Regeln positionieren, die mit dem Präfix `flex` beginnen.
 *
 * Der zu spezifizierende, generische Parameter dient nur dazu, Fluent-APIs zu ermöglichen. D.h. Ableitungen von dieser Klasse müssen sich selbst als
 * Parameter angeben.
 *
 * @author Florian Steitz (fst)
 */
abstract class AbstractFlexLayout<LAYOUT : AbstractFlexLayout<LAYOUT>> : AbstractLayout<LAYOUT>(), SHDFlexLayout<LAYOUT> {

    /**
     * Fügt eine zusätzliche CSS-Klasse zum Wurzelknoten hinzu, die aus dem Wurzlknoten einen sog. `Flexible Box Container` macht.
     */
    init {
        rootNode.withStyleName(BasisTheme.CLASS_FLEX_LAYOUT)
    }

    /**
     * Fügt das übergebene [HTML-Element][HTMLElement] via [AbstractLayout.add] an das Ende der Liste der Kindknoten dieser Layout-Komponente hinzu.
     * Falls diese Layout-Komponente bereits mindestens einen Kindknoten enthält, wird zusätzlich noch ein sog. "Spacing-Div" vor das hinzuzufügende
     * HTML-Element zur Liste der Kindknoten dieser Layout-Komponente hinzugefügt.
     */
    @Suppress("UNCHECKED_CAST")
    override fun add(element: HTMLElement): LAYOUT {
        ensureSpacing(rootNode::appendChild)
        return super<AbstractLayout>.add(element)
    }

    /**
     * Fügt das übergebene [HTML-Element][HTMLElement] via [AbstractLayout.addAsFirst] an den Anfang der Liste der Kindknoten dieser Layout-Komponente
     * hinzu. Falls diese Layout-Komponente bereits mindestens einen Kindknoten enthält, wird zusätzlich noch ein sog. "Spacing-Div" hinter das
     * hinzuzufügende HTML-Element zur Liste der Kindknoten dieser Layout-Komponente hinzugefügt.
     */
    override fun addAsFirst(element: HTMLElement): LAYOUT {
        ensureSpacing(rootNode::prependChild)
        return super<AbstractLayout>.addAsFirst(element)
    }

    /**
     * Entfernt das übergebene [HTML-Element][HTMLElement] via [AbstractLayout.remove] aus der Liste der Kindknoten dieser Layout-Komponente. Falls
     * diese Layout-Komponente mindestens einen weiteren Kindknoten enthält, wird zusätzlich noch ein sog. "Spacing-Div", das sich neben dem zu
     * entfernenden HTML-Element befindet, aus der Liste der Kindknoten dieser Layout-Komponente entfernt.
     */
    override fun remove(element: HTMLElement): LAYOUT {
        val previousSibling = element.previousSibling

        // Falls es kein vorheriges Element gibt, dann wird das folgende Spacing-Div entfernt. Sofern eins vorhanden ist.
        if (previousSibling == null) {
            val nextSibling = element.nextSibling

            if (nextSibling != null) {
                rootNode.removeChild(nextSibling)
            }
        } else {
            rootNode.removeChild(previousSibling)
        }

        return super<AbstractLayout>.remove(element)
    }

    override fun contains(node: Node): Boolean {
        return rootNode.contains(node)
    }

    override fun contains(component: SHDUIComponent): Boolean {
        return contains(component.rootNode)
    }

    @Suppress("UNCHECKED_CAST")
    override fun addAsExpanded(element: HTMLElement): LAYOUT {
        withExpandRatio(element, 1)
        element.withFullWidth()
        add(element)

        return this as LAYOUT
    }

    @Suppress("UNCHECKED_CAST")
    override fun withExpandRatio(element: HTMLElement, ratio: Double): LAYOUT {
        if (ratio > 1) {
            throw IllegalArgumentException("Die Ratio darf nicht groesser als 1 sein")
        }

        element.style.flex = "${ratio * 100}%"
        return this as LAYOUT
    }

    @Suppress("UNCHECKED_CAST")
    override fun withSpacing(enabled: Boolean): LAYOUT {
        rootNode.classList.toggle(BasisTheme.CLASS_WITH_SPACING, enabled)
        return this as LAYOUT
    }

    /**
     * Stellt sicher, dass über die übergebene Funktion `insert` ein sog. "Spacing-Div" hinzugefügt wird, falls diese Layout-Komponente bereits
     * mindestens einen Kindknoten enthält.
     */
    private fun ensureSpacing(insert: (Element) -> Node?) {
        if (rootNode.childElementCount > 0) {
            insert(nodeFactory.div(BasisTheme.CLASS_SPACING))
        }
    }
}