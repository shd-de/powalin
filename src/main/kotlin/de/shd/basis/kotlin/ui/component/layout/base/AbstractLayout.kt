package de.shd.basis.kotlin.ui.component.layout.base

import de.shd.basis.kotlin.ui.component.AbstractUIComponent
import de.shd.basis.kotlin.ui.theme.BasisTheme
import de.shd.basis.kotlin.ui.util.function.prependChild
import de.shd.basis.kotlin.ui.util.function.removeAllChildren
import kotlinx.html.div
import org.w3c.dom.HTMLElement

/**
 * Die abstrakte Standard-Implementierung von [SHDLayout], von der jede Layout-Komponente erben soll.
 *
 * Der zu spezifizierende, generische Parameter dient nur dazu, Fluent-APIs zu ermöglichen. D.h. Ableitungen von dieser Klasse müssen sich selbst als
 * Parameter angeben.
 *
 * @author Florian Steitz (fst)
 */
abstract class AbstractLayout<LAYOUT : AbstractLayout<LAYOUT>> : AbstractUIComponent<LAYOUT>(), SHDLayout<LAYOUT> {

    // Eine Layout-Komponente soll immer ein "div" als Wurzelknoten haben. Und eine ableitende Komponente soll dies auch nicht überschreiben können.
    final override val rootNode: HTMLElement = nodeFactory.div(BasisTheme.CLASS_LAYOUT)

    @Suppress("UNCHECKED_CAST")
    override fun add(element: HTMLElement): LAYOUT {
        rootNode.appendChild(element)
        return this as LAYOUT
    }

    @Suppress("UNCHECKED_CAST")
    override fun addAll(vararg elements: HTMLElement): LAYOUT {
        elements.forEach { add(it) }
        return this as LAYOUT
    }

    @Suppress("UNCHECKED_CAST")
    override fun addAsFirst(element: HTMLElement): LAYOUT {
        rootNode.prependChild(element)
        return this as LAYOUT
    }

    @Suppress("UNCHECKED_CAST")
    override fun remove(element: HTMLElement): LAYOUT {
        rootNode.removeChild(element)
        return this as LAYOUT
    }

    @Suppress("UNCHECKED_CAST")
    override fun clear(): LAYOUT {
        rootNode.removeAllChildren()
        return this as LAYOUT
    }
}