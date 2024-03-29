package de.shd.basis.kotlin.ui.component

import de.shd.basis.kotlin.ui.css.CSSDisplay
import de.shd.basis.kotlin.ui.css.CSSFloat
import de.shd.basis.kotlin.ui.css.CSSPosition
import de.shd.basis.kotlin.ui.css.CSSTextAlign
import de.shd.basis.kotlin.ui.css.CSSUnit
import de.shd.basis.kotlin.ui.css.CSSWhiteSpace
import de.shd.basis.kotlin.ui.util.function.clearStyle
import de.shd.basis.kotlin.ui.util.function.clearStyleNames
import de.shd.basis.kotlin.ui.util.function.getDisplay
import de.shd.basis.kotlin.ui.util.function.isVisible
import de.shd.basis.kotlin.ui.util.function.removeStyleName
import de.shd.basis.kotlin.ui.util.function.removeStyleNames
import de.shd.basis.kotlin.ui.util.function.toggleStyleName
import de.shd.basis.kotlin.ui.util.function.withAttribute
import de.shd.basis.kotlin.ui.util.function.withColor
import de.shd.basis.kotlin.ui.util.function.withDisplay
import de.shd.basis.kotlin.ui.util.function.withFloat
import de.shd.basis.kotlin.ui.util.function.withFontSize
import de.shd.basis.kotlin.ui.util.function.withFontWeight
import de.shd.basis.kotlin.ui.util.function.withFullHeight
import de.shd.basis.kotlin.ui.util.function.withFullWidth
import de.shd.basis.kotlin.ui.util.function.withHeight
import de.shd.basis.kotlin.ui.util.function.withHeightUndefined
import de.shd.basis.kotlin.ui.util.function.withHiddenOverflow
import de.shd.basis.kotlin.ui.util.function.withID
import de.shd.basis.kotlin.ui.util.function.withLineHeight
import de.shd.basis.kotlin.ui.util.function.withMargin
import de.shd.basis.kotlin.ui.util.function.withMarginBottom
import de.shd.basis.kotlin.ui.util.function.withMarginBottomAuto
import de.shd.basis.kotlin.ui.util.function.withMarginLeft
import de.shd.basis.kotlin.ui.util.function.withMarginRight
import de.shd.basis.kotlin.ui.util.function.withMarginTop
import de.shd.basis.kotlin.ui.util.function.withMarginTopAuto
import de.shd.basis.kotlin.ui.util.function.withMinHeight
import de.shd.basis.kotlin.ui.util.function.withMinWidth
import de.shd.basis.kotlin.ui.util.function.withPadding
import de.shd.basis.kotlin.ui.util.function.withPaddingBottom
import de.shd.basis.kotlin.ui.util.function.withPaddingLeft
import de.shd.basis.kotlin.ui.util.function.withPaddingRight
import de.shd.basis.kotlin.ui.util.function.withPaddingTop
import de.shd.basis.kotlin.ui.util.function.withPosition
import de.shd.basis.kotlin.ui.util.function.withSizeFull
import de.shd.basis.kotlin.ui.util.function.withSizeUndefined
import de.shd.basis.kotlin.ui.util.function.withStyle
import de.shd.basis.kotlin.ui.util.function.withStyleName
import de.shd.basis.kotlin.ui.util.function.withStyleNames
import de.shd.basis.kotlin.ui.util.function.withTextAlign
import de.shd.basis.kotlin.ui.util.function.withVisible
import de.shd.basis.kotlin.ui.util.function.withWhiteSpace
import de.shd.basis.kotlin.ui.util.function.withWidth
import de.shd.basis.kotlin.ui.util.function.withWidthUndefined
import de.shd.basis.kotlin.ui.util.function.withoutAttribute
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.CSSStyleDeclaration

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
     * Ruft intern die Methode [HTMLElement.withMinWidth] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMinWidth(width: Int, unit: CSSUnit): COMPONENT {
        rootNode.withMinWidth(width, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withMinWidth] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMinWidth(width: Double, unit: CSSUnit): COMPONENT {
        rootNode.withMinWidth(width, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withWidthUndefined] des Wurzelknotens von dieser UI-Komponente auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withWidthUndefined(): COMPONENT {
        rootNode.withWidthUndefined()
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
     * Ruft intern die Methode [HTMLElement.withHeight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMinHeight(height: Int, unit: CSSUnit): COMPONENT {
        rootNode.withMinHeight(height, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withHeight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMinHeight(height: Double, unit: CSSUnit): COMPONENT {
        rootNode.withMinHeight(height, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withHeightUndefined] des Wurzelknotens von dieser UI-Komponente auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withHeightUndefined(): COMPONENT {
        rootNode.withHeightUndefined()
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withSizeFull] des Wurzelknotens von dieser UI-Komponente auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withSizeFull(): COMPONENT {
        rootNode.withSizeFull()
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withSizeUndefined] des Wurzelknotens von dieser UI-Komponente auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withSizeUndefined(): COMPONENT {
        rootNode.withSizeUndefined()
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withLineHeight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withLineHeight(lineHeight: Int, unit: CSSUnit): COMPONENT {
        rootNode.withLineHeight(lineHeight, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withLineHeight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withLineHeight(lineHeight: Double, unit: CSSUnit): COMPONENT {
        rootNode.withLineHeight(lineHeight, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withFontSize] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withFontSize(fontSize: Int, unit: CSSUnit): COMPONENT {
        rootNode.withFontSize(fontSize, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withFontSize] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withFontSize(fontSize: Double, unit: CSSUnit): COMPONENT {
        rootNode.withFontSize(fontSize, unit)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withFontWeight] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withFontWeight(fontWeight: Int): COMPONENT {
        rootNode.withFontWeight(fontWeight)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withColor] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withColor(color: String): COMPONENT {
        rootNode.withColor(color)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withTextAlign] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withTextAlign(textAlign: CSSTextAlign): COMPONENT {
        rootNode.withTextAlign(textAlign)
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
     * Ruft intern die Methode [HTMLElement.withMarginTopAuto] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginTopAuto(): COMPONENT {
        rootNode.withMarginTopAuto()
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
     * Ruft intern die Methode [HTMLElement.withMarginBottomAuto] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withMarginBottomAuto(): COMPONENT {
        rootNode.withMarginBottomAuto()
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
     * Ruft intern die Methode [HTMLElement.withHiddenOverflow] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withHiddenOverflow(): COMPONENT {
        rootNode.withHiddenOverflow()
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withFloat] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withFloat(float: CSSFloat): COMPONENT {
        rootNode.withFloat(float)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withWhiteSpace] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withWhiteSpace(whiteSpace: CSSWhiteSpace): COMPONENT {
        rootNode.withWhiteSpace(whiteSpace)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withPosition] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withPosition(position: CSSPosition): COMPONENT {
        rootNode.withPosition(position)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.removeStyleName] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun removeStyleName(styleName: String): COMPONENT {
        rootNode.removeStyleName(styleName)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.removeStyleNames] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun removeStyleNames(vararg styleNames: String): COMPONENT {
        rootNode.removeStyleNames(*styleNames)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.toggleStyleName] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun toggleStyleName(styleName: String): COMPONENT {
        rootNode.toggleStyleName(styleName)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.toggleStyleName] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun toggleStyleName(styleName: String, add: Boolean): COMPONENT {
        rootNode.toggleStyleName(styleName, add)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.clearStyle] des Wurzelknotens von dieser UI-Komponente auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun clearStyle(): COMPONENT {
        rootNode.clearStyle()
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.clearStyleNames] des Wurzelknotens von dieser UI-Komponente auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun clearStyleNames(): COMPONENT {
        rootNode.clearStyleNames()
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

    /**
     * Ruft intern die Methode [HTMLElement.withStyle] des Wurzelknotens von dieser UI-Komponente mit dem übergebenen Argument auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withStyle(configure: CSSStyleDeclaration.() -> Unit): COMPONENT {
        rootNode.withStyle(configure)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withAttribute] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withAttribute(attribute: String, value: String): COMPONENT {
        rootNode.withAttribute(attribute, value)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.withAttribute] des Wurzelknotens von dieser UI-Komponente mit den übergebenen Argumenten auf.
     */
    @Suppress("UNCHECKED_CAST")
    fun withoutAttribute(attribute: String): COMPONENT {
        rootNode.withoutAttribute(attribute)
        return this as COMPONENT
    }

    /**
     * Ruft intern die Methode [HTMLElement.getDisplay] des Wurzelknotens von dieser UI-Komponente auf und gibt deren Rückgabewert zurück.
     */
    fun getDisplay(): CSSDisplay? {
        return rootNode.getDisplay()
    }

    /**
     * Ruft intern die Methode [HTMLElement.isVisible] des Wurzelknotens von dieser UI-Komponente auf und gibt deren Rückgabewert zurück.
     */
    fun isVisible(): Boolean {
        return rootNode.isVisible()
    }

    /**
     * Ruft intern die Methode [HTMLElement.focus] des Wurzelknotens von dieser UI-Komponente auf, wodurch dieses Element fokussiert wird, sofern es
     * denn fokussiert werden kann.
     */
    @Suppress("UNCHECKED_CAST")
    fun focus(): COMPONENT {
        rootNode.focus()
        return this as COMPONENT
    }
}