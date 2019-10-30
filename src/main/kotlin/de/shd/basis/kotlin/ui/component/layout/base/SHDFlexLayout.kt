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
     * Fügt das übergebene [HTML-Element][HTMLElement] via [add] an das Ende der Liste der Kindknoten dieser Layout-Komponente hinzu und legt via
     * [withExpandRatio] fest, dass das übergebene HTML-Element den verbleibenden Platz einnehmen soll, der Kindknoten dieser Layout-Komponente
     * insgesamt noch zur Verfügung steht. Darüber hinaus wird die Breite des übergebenen HTML-Elements auf `100%` gesetzt.
     */
    fun addAsExpanded(element: HTMLElement): LAYOUT

    /**
     * Bestimmt, wie viel in Prozent vom verbleibenden Platz, der Kindelementen bzw. Kindkomponenten dieser Layout-Komponente insgesamt noch zur
     * Verfügung steht, das übergebene [HTML-Element][HTMLElement] einnehmen soll. Falls alle Kindelemente bzw. Kindkomponenten dieser Layout-Komponente
     * die gleiche Expand-Ratio haben, wird der Platz dieser Layout-Komponente gleichmäßig unter ihnen aufgeteilt. Standardmäßig nehmen ihre
     * Kindelemente bzw. Kindkomponenten aber den Platz ein, der ihrer tatsächlichen Größe entspricht.
     *
     * Der übergebene Wert muss dabei zwischen 0 und 1 liegen (also 0% - 100%).
     *
     * Nähere Details hierzu könnnen [hier](https://developer.mozilla.org/en-US/docs/Web/CSS/flex) nachgelesen werden.
     */
    fun withExpandRatio(element: HTMLElement, ratio: Double): LAYOUT

    /**
     * Bestimmt, ob Standard-Abstände (jeweils `0.75em`) zwischen den Kindknoten dieser Layout-Komponente angezeigt werden sollen. Standardmäßig
     * werden keine Abstände angezeigt. D.h. standardmäßig werden Kindknoten dieser Layout-Komponente direkt aneinander angezeigt.
     */
    fun withSpacing(enabled: Boolean): LAYOUT

    /**
     * Bestimmt, ob die Standard-Abstände zwischen den Kindknoten dieser Layout-Komponente eine Mindesthöhe bzw.
     * Mindestbreite von `0.75em` erhalten sollen.
     */
    fun withSpacingMinSizeEnabled(enabled: Boolean): LAYOUT

    /**
     * Fügt die übergebene [UI-Komponente][SHDUIComponent] via [add] an das Ende der Liste der Kindknoten dieser Layout-Komponente hinzu und legt via
     * [withExpandRatio] fest, dass die übergebene UI-Komponente den verbleibenden Platz einnehmen soll, der Kindknoten dieser Layout-Komponente
     * insgesamt noch zur Verfügung steht. Darüber hinaus wird die Breite der übergebenen UI-Komponente auf `100%` gesetzt.
     */
    @Suppress("UNCHECKED_CAST")
    fun addAsExpanded(component: SHDUIComponent): LAYOUT {
        add(component.rootNode)
        return this as LAYOUT
    }

    /**
     * Bestimmt, wie viel in Prozent vom verbleibenden Platz, der Kindelementen bzw. Kindkomponenten dieser Layout-Komponente insgesamt noch zur
     * Verfügung steht, das übergebene [HTML-Element][HTMLElement] einnehmen soll. Falls alle Kindelemente bzw. Kindkomponenten dieser Layout-Komponente
     * die gleiche Expand-Ratio haben, wird der Platz dieser Layout-Komponente gleichmäßig unter ihnen aufgeteilt. Standardmäßig nehmen ihre
     * Kindelemente bzw. Kindkomponenten aber den Platz ein, der ihrer tatsächlichen Größe entspricht.
     *
     * Der übergebene Wert muss dabei zwischen 0 und 1 liegen (also 0% - 100%).
     *
     * Nähere Details hierzu könnnen [hier](https://developer.mozilla.org/en-US/docs/Web/CSS/flex) nachgelesen werden.
     */
    @Suppress("UNCHECKED_CAST")
    fun withExpandRatio(element: HTMLElement, ratio: Int): LAYOUT {
        withExpandRatio(element, ratio.toDouble())
        return this as LAYOUT
    }

    /**
     * Bestimmt, wie viel in Prozent vom verbleibenden Platz, der Kindelementen bzw. Kindkomponenten dieser Layout-Komponente insgesamt noch zur
     * Verfügung steht, das übergebene [HTML-Element][HTMLElement] einnehmen soll. Falls alle Kindelemente bzw. Kindkomponenten dieser Layout-Komponente
     * die gleiche Expand-Ratio haben, wird der Platz dieser Layout-Komponente gleichmäßig unter ihnen aufgeteilt. Standardmäßig nehmen ihre
     * Kindelemente bzw. Kindkomponenten aber den Platz ein, der ihrer tatsächlichen Größe entspricht.
     *
     * Der übergebene Wert muss dabei zwischen 0 und 1 liegen (also 0% - 100%).
     *
     * Nähere Details hierzu könnnen [hier](https://developer.mozilla.org/en-US/docs/Web/CSS/flex) nachgelesen werden.
     */
    @Suppress("UNCHECKED_CAST")
    fun withExpandRatio(component: SHDUIComponent, ratio: Int): LAYOUT {
        withExpandRatio(component.rootNode, ratio)
        return this as LAYOUT
    }

    /**
     * Bestimmt, wie viel in Prozent vom verbleibenden Platz, der Kindelementen bzw. Kindkomponenten dieser Layout-Komponente insgesamt noch zur
     * Verfügung steht, das übergebene [HTML-Element][HTMLElement] einnehmen soll. Falls alle Kindelemente bzw. Kindkomponenten dieser Layout-Komponente
     * die gleiche Expand-Ratio haben, wird der Platz dieser Layout-Komponente gleichmäßig unter ihnen aufgeteilt. Standardmäßig nehmen ihre
     * Kindelemente bzw. Kindkomponenten aber den Platz ein, der ihrer tatsächlichen Größe entspricht.
     *
     * Der übergebene Wert muss dabei zwischen 0 und 1 liegen (also 0% - 100%).
     *
     * Nähere Details hierzu könnnen [hier](https://developer.mozilla.org/en-US/docs/Web/CSS/flex) nachgelesen werden.
     */
    @Suppress("UNCHECKED_CAST")
    fun withExpandRatio(component: SHDUIComponent, ratio: Double): LAYOUT {
        withExpandRatio(component.rootNode, ratio)
        return this as LAYOUT
    }
}
