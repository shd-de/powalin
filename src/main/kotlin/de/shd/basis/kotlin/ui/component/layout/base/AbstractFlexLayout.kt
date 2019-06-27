package de.shd.basis.kotlin.ui.component.layout.base

import de.shd.basis.kotlin.ui.css.CSSUnit
import de.shd.basis.kotlin.ui.theme.BasisTheme
import de.shd.basis.kotlin.ui.util.function.withChild
import de.shd.basis.kotlin.ui.util.function.withStyleName
import de.shd.basis.kotlin.ui.util.function.withWidth
import kotlinx.html.div
import org.w3c.dom.HTMLElement

/**
 *
 * @author Florian Steitz (fst)
 */
abstract class AbstractFlexLayout<LAYOUT : AbstractFlexLayout<LAYOUT>> : AbstractLayout<LAYOUT>(), SHDFlexLayout<LAYOUT> {

    init {
        rootNode.withStyleName(BasisTheme.CLASS_FLEX_LAYOUT)
    }

    @Suppress("UNCHECKED_CAST")
    override fun add(element: HTMLElement): LAYOUT {
        if (rootNode.childElementCount > 0) {
            rootNode.withChild(nodeFactory.div(BasisTheme.CLASS_SPACING))
        }

        super<AbstractLayout>.add(element)
        return this as LAYOUT
    }

    @Suppress("UNCHECKED_CAST")
    override fun addAsExpanded(element: HTMLElement): LAYOUT {
        element.withWidth(100, CSSUnit.PERCENTAGE)
        withExpandRatio(element, 1)
        add(element)

        return this as LAYOUT
    }

    @Suppress("UNCHECKED_CAST")
    override fun withExpandRatio(element: HTMLElement, ratio: Double): LAYOUT {
        element.style.flexGrow = ratio.toString()
        return this as LAYOUT
    }

    @Suppress("UNCHECKED_CAST")
    override fun withSpacing(enabled: Boolean): LAYOUT {
        rootNode.classList.toggle(BasisTheme.CLASS_WITH_SPACING, enabled)
        return this as LAYOUT
    }
}