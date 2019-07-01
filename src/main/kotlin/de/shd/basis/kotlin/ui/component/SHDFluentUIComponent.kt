package de.shd.basis.kotlin.ui.component

import de.shd.basis.kotlin.ui.css.CSSDisplay
import de.shd.basis.kotlin.ui.css.CSSUnit
import de.shd.basis.kotlin.ui.util.function.withDisplay
import de.shd.basis.kotlin.ui.util.function.withFullHeight
import de.shd.basis.kotlin.ui.util.function.withFullWidth
import de.shd.basis.kotlin.ui.util.function.withHeight
import de.shd.basis.kotlin.ui.util.function.withID
import de.shd.basis.kotlin.ui.util.function.withMargin
import de.shd.basis.kotlin.ui.util.function.withMarginBottom
import de.shd.basis.kotlin.ui.util.function.withMarginLeft
import de.shd.basis.kotlin.ui.util.function.withMarginRight
import de.shd.basis.kotlin.ui.util.function.withMarginTop
import de.shd.basis.kotlin.ui.util.function.withPadding
import de.shd.basis.kotlin.ui.util.function.withPaddingBottom
import de.shd.basis.kotlin.ui.util.function.withPaddingLeft
import de.shd.basis.kotlin.ui.util.function.withPaddingRight
import de.shd.basis.kotlin.ui.util.function.withPaddingTop
import de.shd.basis.kotlin.ui.util.function.withStyleName
import de.shd.basis.kotlin.ui.util.function.withStyleNames
import de.shd.basis.kotlin.ui.util.function.withVisible
import de.shd.basis.kotlin.ui.util.function.withWidth
import org.w3c.dom.HTMLElement

/**
 * Ein Mixin-Interface für Implementierungen von [SHDUIComponent], das die Extension-Functions mit dem Präfix `with` des [Wurzelknotens][HTMLElement]
 * der implementierenden Komponente zur öffentlichen API der Komponente selbst hinzufügt. D.h. diese Funktionen können direkt aufgerufen werden, ohne
 * zuvor auf den [rootNode] der Komponente zugreifen zu müssen.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
interface SHDFluentUIComponent<COMPONENT : SHDFluentUIComponent<COMPONENT>> : SHDUIComponent {

    /**
     * Ruft intern die Methode [HTMLElement.withFullWidth] des Wurzelknotens von dieser UI-Komponente auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withFullWidth(): COMPONENT {
        rootNode.withFullWidth()
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withWidth] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withWidth(width: Int, unit: CSSUnit): COMPONENT {
        rootNode.withWidth(width, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withWidth] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withWidth(width: Double, unit: CSSUnit): COMPONENT {
        rootNode.withWidth(width, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withFullHeight] des Wurzelknotens von dieser UI-Komponente auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withFullHeight(): COMPONENT {
        rootNode.withFullHeight()
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withHeight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withHeight(height: Int, unit: CSSUnit): COMPONENT {
        rootNode.withHeight(height, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withHeight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withHeight(height: Double, unit: CSSUnit): COMPONENT {
        rootNode.withHeight(height, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPadding] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPadding(padding: Int, unit: CSSUnit): COMPONENT {
        rootNode.withPadding(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPadding] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPadding(padding: Double, unit: CSSUnit): COMPONENT {
        rootNode.withPadding(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPaddingTop] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPaddingTop(padding: Int, unit: CSSUnit): COMPONENT {
        rootNode.withPaddingTop(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPaddingTop] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPaddingTop(padding: Double, unit: CSSUnit): COMPONENT {
        rootNode.withPaddingTop(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPaddingRight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPaddingRight(padding: Int, unit: CSSUnit): COMPONENT {
        rootNode.withPaddingRight(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPaddingRight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPaddingRight(padding: Double, unit: CSSUnit): COMPONENT {
        rootNode.withPaddingRight(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPaddingBottom] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPaddingBottom(padding: Int, unit: CSSUnit): COMPONENT {
        rootNode.withPaddingBottom(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPaddingBottom] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPaddingBottom(padding: Double, unit: CSSUnit): COMPONENT {
        rootNode.withPaddingBottom(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPaddingLeft] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPaddingLeft(padding: Int, unit: CSSUnit): COMPONENT {
        rootNode.withPaddingLeft(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPaddingLeft] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPaddingLeft(padding: Double, unit: CSSUnit): COMPONENT {
        rootNode.withPaddingLeft(padding, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMargin] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMargin(margin: Int, unit: CSSUnit): COMPONENT {
        rootNode.withMargin(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMargin] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMargin(margin: Double, unit: CSSUnit): COMPONENT {
        rootNode.withMargin(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMarginTop] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginTop(margin: Int, unit: CSSUnit): COMPONENT {
        rootNode.withMarginTop(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMarginTop] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginTop(margin: Double, unit: CSSUnit): COMPONENT {
        rootNode.withMarginTop(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMarginRight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginRight(margin: Int, unit: CSSUnit): COMPONENT {
        rootNode.withMarginRight(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMarginRight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginRight(margin: Double, unit: CSSUnit): COMPONENT {
        rootNode.withMarginRight(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMarginBottom] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginBottom(margin: Int, unit: CSSUnit): COMPONENT {
        rootNode.withMarginBottom(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMarginBottom] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginBottom(margin: Double, unit: CSSUnit): COMPONENT {
        rootNode.withMarginBottom(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMarginLeft] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginLeft(margin: Int, unit: CSSUnit): COMPONENT {
        rootNode.withMarginLeft(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMarginLeft] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginLeft(margin: Double, unit: CSSUnit): COMPONENT {
        rootNode.withMarginLeft(margin, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withVisible] des Wurzelknotens von dieser UI-Komponente mit dem übergebenen Argument auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withVisible(visible: Boolean): COMPONENT {
        rootNode.withVisible(visible)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withDisplay] des Wurzelknotens von dieser UI-Komponente mit dem übergebenen Argument auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withDisplay(display: CSSDisplay): COMPONENT {
        rootNode.withDisplay(display)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withStyleName] des Wurzelknotens von dieser UI-Komponente mit dem übergebenen Argument auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withStyleName(styleName: String): COMPONENT {
        rootNode.withStyleName(styleName)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withStyleNames] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withStyleNames(vararg styleNames: String): COMPONENT {
        rootNode.withStyleNames(*styleNames)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withID] des Wurzelknotens von dieser UI-Komponente mit dem übergebenen Argument auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withID(id: String): COMPONENT {
        rootNode.withID(id)
        return this as COMPONENT
    }
}