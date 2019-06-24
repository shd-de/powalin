package de.shd.basis.kotlin.ui.util.function

import de.shd.basis.kotlin.ui.css.CSSDisplay
import de.shd.basis.kotlin.ui.css.CSSUnit
import de.shd.basis.kotlin.ui.mvc.view.MVCView
import de.shd.basis.kotlin.ui.util.constant.EMPTY_STRING
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import org.w3c.dom.css.CSSStyleDeclaration
import org.w3c.dom.events.EventTarget

/**
 * Fügt die übergebene [MVCView] via [Node.appendChild] an das Ende der Liste der Kindknoten dieses Elements hinzu.
 *
 * @see MVCView.rootNode
 * @author Florian Steitz (fst)
 */
fun Node.appendChild(view: MVCView) {
    appendChild(view.rootNode)
}

/**
 * Entfernt alle Kindknoten dieses Elements.
 *
 * @see Node.removeChild
 * @author Florian Steitz (fst)
 */
fun Node.removeAllChildren() {
    while (firstChild != null) {
        removeChild(firstChild as Node)
    }
}

/**
 * Fügt einen Event-Listener zu diesem Element hinzu, der ausgeführt wird, wenn ein Anwender auf dieses Element klickt.
 *
 * @see EventTarget.addEventListener
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun Node.addClickListener(listener: () -> Unit) {
    addEventListener("click", { listener() })
}

/**
 * Setzt die Breite des Inhaltsbereichs dieses Elements auf 100%. Der Inhaltsbereich ist innerhalb des Innenabstands, Rahmens und Außenabstands dieses
 * Elements.
 *
 * @see CSSStyleDeclaration.width
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withWidthFull(): HTMLElement {
    return withWidth(100, CSSUnit.PERCENTAGE)
}

/**
 * Legt die Breite des Inhaltsbereichs dieses Elements fest. Der Inhaltsbereich ist innerhalb des Innenabstands, Rahmens und Außenabstands dieses
 * Elements.
 *
 * @see CSSStyleDeclaration.width
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withWidth(width: Int, unit: CSSUnit): HTMLElement {
    return withWidth(width.toDouble(), unit)
}

/**
 * Legt die Breite des Inhaltsbereichs dieses Elements fest. Der Inhaltsbereich ist innerhalb des Innenabstands, Rahmens und Außenabstands dieses
 * Elements.
 *
 * @see CSSStyleDeclaration.width
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withWidth(width: Double, unit: CSSUnit): HTMLElement {
    this.style.width = joinValueWithUnit(width, unit)
    return this
}

/**
 * Setzt die Höhe des Inhaltsbereichs dieses Elements auf 100%. Der Inhaltsbereich ist innerhalb des Innenabstands, Rahmens und Außenabstands dieses
 * Elements.
 *
 * @see CSSStyleDeclaration.height
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withHeightFull(): HTMLElement {
    return withHeight(100, CSSUnit.PERCENTAGE)
}

/**
 * Legt die Höhe des Inhaltsbereichs dieses Elements fest. Der Inhaltsbereich ist innerhalb des Innenabstands, Rahmens und Außenabstands dieses
 * Elements.
 *
 * @see CSSStyleDeclaration.height
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withHeight(height: Int, unit: CSSUnit): HTMLElement {
    return withHeight(height.toDouble(), unit)
}

/**
 * Legt die Höhe des Inhaltsbereichs dieses Elements fest. Der Inhaltsbereich ist innerhalb des Innenabstands, Rahmens und Außenabstands dieses
 * Elements.
 *
 * @see CSSStyleDeclaration.height
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withHeight(height: Double, unit: CSSUnit): HTMLElement {
    this.style.height = joinValueWithUnit(height, unit)
    return this
}

/**
 * Legt die Innenabstände aller vier Seiten dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.padding
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withPadding(padding: Int, unit: CSSUnit): HTMLElement {
    return withPadding(padding.toDouble(), unit)
}

/**
 * Legt die Innenabstände aller vier Seiten dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.padding
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withPadding(padding: Double, unit: CSSUnit): HTMLElement {
    this.style.padding = joinValueWithUnit(padding, unit)
    return this
}

/**
 * Legt den oberen Innenabstand dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.paddingTop
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withPaddingTop(padding: Int, unit: CSSUnit): HTMLElement {
    return withPaddingTop(padding.toDouble(), unit)
}

/**
 * Legt den oberen Innenabstand dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.paddingTop
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withPaddingTop(padding: Double, unit: CSSUnit): HTMLElement {
    this.style.paddingTop = joinValueWithUnit(padding, unit)
    return this
}

/**
 * Legt den rechten Innenabstand dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.paddingRight
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withPaddingRight(padding: Int, unit: CSSUnit): HTMLElement {
    return withPaddingRight(padding.toDouble(), unit)
}

/**
 * Legt den rechten Innenabstand dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.paddingRight
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withPaddingRight(padding: Double, unit: CSSUnit): HTMLElement {
    this.style.paddingRight = joinValueWithUnit(padding, unit)
    return this
}

/**
 * Legt den unteren Innenabstand dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.paddingBottom
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withPaddingBottom(padding: Int, unit: CSSUnit): HTMLElement {
    return withPaddingBottom(padding.toDouble(), unit)
}

/**
 * Legt den unteren Innenabstand dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.paddingBottom
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withPaddingBottom(padding: Double, unit: CSSUnit): HTMLElement {
    this.style.paddingBottom = joinValueWithUnit(padding, unit)
    return this
}

/**
 * Legt den linken Innenabstand dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.paddingLeft
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withPaddingLeft(padding: Int, unit: CSSUnit): HTMLElement {
    return withPaddingLeft(padding.toDouble(), unit)
}

/**
 * Legt den linken Innenabstand dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.paddingLeft
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withPaddingLeft(padding: Double, unit: CSSUnit): HTMLElement {
    this.style.paddingLeft = joinValueWithUnit(padding, unit)
    return this
}

/**
 * Legt die Außenabstände aller vier Seiten dieses Elements fest.
 *
 * @see CSSStyleDeclaration.margin
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withMargin(margin: Int, unit: CSSUnit): HTMLElement {
    return withMargin(margin.toDouble(), unit)
}

/**
 * Legt die Außenabstände aller vier Seiten dieses Elements fest.
 *
 * @see CSSStyleDeclaration.margin
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withMargin(margin: Double, unit: CSSUnit): HTMLElement {
    this.style.margin = joinValueWithUnit(margin, unit)
    return this
}

/**
 * Legt den oberen Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginTop
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withMarginTop(margin: Int, unit: CSSUnit): HTMLElement {
    return withMarginTop(margin.toDouble(), unit)
}

/**
 * Legt den oberen Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginTop
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withMarginTop(margin: Double, unit: CSSUnit): HTMLElement {
    this.style.marginTop = joinValueWithUnit(margin, unit)
    return this
}

/**
 * Legt den rechten Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginRight
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withMarginRight(margin: Int, unit: CSSUnit): HTMLElement {
    return withMarginRight(margin.toDouble(), unit)
}

/**
 * Legt den rechten Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginRight
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withMarginRight(margin: Double, unit: CSSUnit): HTMLElement {
    this.style.marginRight = joinValueWithUnit(margin, unit)
    return this
}

/**
 * Legt den unteren Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginBottom
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withMarginBottom(margin: Int, unit: CSSUnit): HTMLElement {
    return withMarginBottom(margin.toDouble(), unit)
}

/**
 * Legt den unteren Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginBottom
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withMarginBottom(margin: Double, unit: CSSUnit): HTMLElement {
    this.style.marginBottom = joinValueWithUnit(margin, unit)
    return this
}

/**
 * Legt den linken Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginLeft
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withMarginLeft(margin: Int, unit: CSSUnit): HTMLElement {
    return withMarginLeft(margin.toDouble(), unit)
}

/**
 * Legt den linken Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginLeft
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withMarginLeft(margin: Double, unit: CSSUnit): HTMLElement {
    this.style.marginLeft = joinValueWithUnit(margin, unit)
    return this
}

/**
 * Legt fest, ob dieses Element sichtbar sein soll oder nicht. Falls nicht, dann wird die Eigenschaft `display` dieses Elements auf `none` gesetzt.
 *
 * Diese Methode sollte nicht zusammen mit [withDisplay] vom selben Element verwendet werden, da diese Methode, abhängig vom Argument, den Wert der
 * Eigenschaft `display` überschreibt, ohne sich einen alten Wert zu merken.
 *
 * @see CSSDisplay.NONE
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withVisible(visible: Boolean): HTMLElement {
    withDisplay(if (visible) EMPTY_STRING else CSSDisplay.NONE.value)
    return this
}

/**
 * Legt den Wert der Eigenschaft `display` dieses Elements fest.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withDisplay(display: CSSDisplay): HTMLElement {
    withDisplay(display.value)
    return this
}

/**
 * Legt den Wert der Eigenschaft `display` dieses Elements fest.
 *
 * @author Florian Steitz (fst)
 */
private fun HTMLElement.withDisplay(display: String): HTMLElement {
    this.style.display = display
    return this
}

/**
 * Konkateniert den übergebenen Wert sowie die übergebene Einheit und gibt den resultierenden String zurück.
 *
 * @author Florian Steitz (fst)
 */
private fun joinValueWithUnit(value: Double, unit: CSSUnit): String {
    return "$value${unit.value}"
}