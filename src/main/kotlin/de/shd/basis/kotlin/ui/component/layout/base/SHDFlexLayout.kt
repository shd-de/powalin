package de.shd.basis.kotlin.ui.component.layout.base

import de.shd.basis.kotlin.ui.component.SHDUIComponent
import org.w3c.dom.HTMLElement

/**
 * Eine Spezialisierung von [SHDLayout], die auf der [Flexible Box](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Flexible_Box_Layout/Basic_Concepts_of_Flexbox)
 * basiert. D.h. sie positioniert ihre Kindelemente bzw. ihre Kindkomponenten primär mit CSS-Regeln, die mit dem Präfix `flex` beginnen.
 *
 * Der zu spezifizierende, generische Parameter dient nur dazu, Fluent-APIs zu ermöglichen. D.h. Implementierungen von diesem Interface müssen sich
 * selbst als Parameter angeben.
 *
 * @author Florian Steitz (fst)
 */
interface SHDFlexLayout<LAYOUT : SHDFlexLayout<LAYOUT>> : SHDLayout<LAYOUT> {

    /**
     *
     */
    fun addAsExpanded(element: HTMLElement): LAYOUT

    /**
     * Bestimmt, wie viel vom verbleibenden Platz dieser Layout-Komponente das übergebene [HTML-Element][HTMLElement] einnehmen soll. Falls alle
     * Kindkonten dieser Layout-Komponente die gleiche Expand-Ratio haben, wird der Platz dieser Layout-Komponente gleichmäßig unter ihren Kindknoten
     * aufgeteilt. Standardmäßig nehmen ihre Kindknoten den Platz ein, der ihrer tatsächlichen Größe entspricht.
     *
     * Nähere Details hierzu könnnen [hier](https://developer.mozilla.org/en-US/docs/Web/CSS/flex-grow) nachgelesen werden.
     */
    fun withExpandRatio(element: HTMLElement, ratio: Double): LAYOUT

    /**
     * Bestimmt, ob ein Standard-Abstand (`0.75em`) zwischen den Kindknoten dieser Layout-Komponente angezeigt werden soll. Standardmäßig wird kein
     * Abstand angezeigt. D.h. sie werden standardmäßig direkt aneinander angezeigt.
     */
    fun withSpacing(enabled: Boolean): LAYOUT;

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun addAsExpanded(component: SHDUIComponent): LAYOUT {
        add(component.rootNode)
        return this as LAYOUT
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun withExpandRatio(element: HTMLElement, ratio: Int): LAYOUT {
        withExpandRatio(element, ratio.toDouble())
        return this as LAYOUT
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun withExpandRatio(component: SHDUIComponent, ratio: Int): LAYOUT {
        withExpandRatio(component.rootNode, ratio)
        return this as LAYOUT
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun withExpandRatio(component: SHDUIComponent, ratio: Double): LAYOUT {
        withExpandRatio(component.rootNode, ratio)
        return this as LAYOUT
    }
}
