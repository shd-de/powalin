package de.shd.basis.kotlin.ui.util.function

import de.shd.basis.kotlin.ui.component.SHDUIComponent
import de.shd.basis.kotlin.ui.css.CSSDisplay
import de.shd.basis.kotlin.ui.css.CSSUnit
import de.shd.basis.kotlin.ui.util.constant.EMPTY_STRING
import org.w3c.dom.Document
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import org.w3c.dom.css.CSSStyleDeclaration
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget

/**
 * Fügt die übergebene [UI-Komponente][SHDUIComponent] via [Node.appendChild] an das Ende der Liste der Kindknoten dieses Elements hinzu und gibt den
 * Wurzelknoten der übergebenen UI-Komponente analog zu [Node.appendChild] zurück.
 *
 * @see SHDUIComponent.rootNode
 * @author Florian Steitz (fst)
 */
fun Node.appendChild(component: SHDUIComponent): Node {
    return appendChild(component.rootNode)
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
 * Fügt einen Event-Listener zu diesem Element hinzu, der ausgeführt wird, wenn ein Anwender auf dieses Element klickt.
 *
 * @see EventTarget.addEventListener
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun Node.addClickListener(listener: (Event) -> Unit) {
    addEventListener("click", listener)
}

/**
 * Setzt die Breite des Inhaltsbereichs dieses Elements auf 100%. Der Inhaltsbereich ist innerhalb des Innenabstands, Rahmens und Außenabstands dieses
 * Elements.
 *
 * @see CSSStyleDeclaration.width
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withFullWidth(): HTMLElement {
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
fun HTMLElement.withFullHeight(): HTMLElement {
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
 * Fügt den übergebenen, sog. "Style-Namen" zu diesem Element hinzu. Bei einem Style-Namen handelt es sich technisch um eine sog. CSS-Klasse. Über
 * CSS-Klassen kann u.a. die Darstellung dieses Elements geändert bzw. beeinflusst werden.
 *
 * **Darstellungsregeln über eine CSS-Klasse namens `my-style` können wie folgt in einer CSS-Datei definiert werden:**
 * ```
 * .my-style {
 *   background: white;
 *   width: 100%;
 * }
 * ```
 *
 * **Diese CSS-Klasse bzw. dieser Style-Name kann anschließend wie folgt zu diesem Element hinzugefügt werden:**
 * ```
 * myElement.withStyleName("my-style")
 * ```
 *
 * In CSS-Dateien muss ein Punkt vor dem Namen jeder CSS-Klasse sein. Dieser Punkt darf allerdings nicht an diese Methode übergeben werden. D.h. es
 * wird nur der eigentliche Name der CSS-Klasse übergeben.
 *
 * @see HTMLElement.classList
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withStyleName(styleName: String): HTMLElement {
    withStyleNames(styleName)
    return this
}

/**
 * Fügt die übergebenen, sog. "Style-Namen" zu diesem Element hinzu. Bei Style-Namen handelt es sich technisch um sog. CSS-Klassen. Über CSS-Klassen
 * kann u.a. die Darstellung dieses Elements geändert bzw. beeinflusst werden.
 *
 * @see HTMLElement.classList
 * @see [HTMLElement.withStyleName]
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withStyleNames(vararg styleNames: String): HTMLElement {
    classList.add(*styleNames)
    return this
}

/**
 * Setzt die ID dieses Elements. Diese ID muss im gesamten HTML-Dokument eindeutig sein. Eine ID soll nur dann festgelegt werden, wenn dies wirklich
 * notwendig ist.
 *
 * IDs können u.a. dazu verwendet werden, um die Darstellung dieses Elements zu ändern bzw. zu beeinflussen. Im Regelfall sollen dafür aber
 * Style-Namen bzw. CSS-Klassen verwendet werden.
 *
 * Eine ID wird im Regelfall nur dann gesetzt, wenn dieses Element bspw. über [Document.getElementById] oder [Document.querySelector] später eindeutig
 * ermittelbar sein soll bzw. muss.
 *
 * @see HTMLElement.id
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withID(id: String): HTMLElement {
    this.id = id
    return this
}

/**
 * Fügt die übergebene [UI-Komponente][SHDUIComponent] via [appendChild] an das Ende der Liste der Kindknoten dieses Elements hinzu.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withChild(component: SHDUIComponent): HTMLElement {
    appendChild(component)
    return this
}

/**
 * Fügt den übergebenen [Node] via [Node.appendChild] an das Ende der Liste der Kindknoten dieses Elements hinzu.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withChild(node: Node): HTMLElement {
    appendChild(node)
    return this
}

/**
 * Fügt die übergebenen [UI-Komponenten][SHDUIComponent] via [appendChild] an das Ende der Liste der Kindknoten dieses Elements in der übergebenen
 * Reihenfolge hinzu.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withChildren(vararg components: SHDUIComponent): HTMLElement {
    components.forEach { appendChild(it) }
    return this
}

/**
 * Fügt die übergebenen [Nodes][Node] via [Node.appendChild] an das Ende der Liste der Kindknoten dieses Elements in der angegebenen Reihenfolge hinzu.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withChildren(vararg nodes: Node): HTMLElement {
    nodes.forEach { appendChild(it) }
    return this
}

/**
 * Fügt einen Event-Listener zu diesem Element hinzu, der ausgeführt wird, wenn ein Anwender auf dieses Element klickt.
 *
 * @see Node.addClickListener
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withClickListener(listener: () -> Unit): HTMLElement {
    addClickListener(listener)
    return this
}

/**
 * Fügt einen Event-Listener zu diesem Element hinzu, der ausgeführt wird, wenn ein Anwender auf dieses Element klickt.
 *
 * @see Node.addClickListener
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.withClickListener(listener: (Event) -> Unit): HTMLElement {
    addClickListener(listener)
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