package de.shd.basis.kotlin.ui.util.function

import de.shd.basis.kotlin.ui.component.SHDUIComponent
import de.shd.basis.kotlin.ui.css.CSSDisplay
import de.shd.basis.kotlin.ui.css.CSSKeyword
import de.shd.basis.kotlin.ui.css.CSSTextAlign
import de.shd.basis.kotlin.ui.css.CSSUnit
import de.shd.basis.kotlin.ui.util.constant.EMPTY_STRING
import kotlinx.html.HTMLTag
import org.w3c.dom.DOMTokenList
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.Node
import org.w3c.dom.css.CSSStyleDeclaration
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget
import org.w3c.dom.get
import kotlin.dom.removeClass

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
fun Node.addClickListener(listener: () -> Unit) {
    addClickListener { _ -> listener() }
}

/**
 * Fügt einen Event-Listener zu diesem Element hinzu, der ausgeführt wird, wenn ein Anwender auf dieses Element klickt.
 *
 * @see EventTarget.addEventListener
 * @author Marcel Ziganow (zim)
 */
fun Node.addClickListener(listener: (Event) -> Unit) {
    addEventListener("click", listener)
}

/**
 * Fügt das übergebene [Element][Element] via [Element.insertAdjacentElement] an den Anfang der Liste der Kindknoten dieses Elements hinzu und gibt
 * das hinzugefügte Element zurück. Oder `null`, falls das Element nicht hinzugefügt werden konnte.
 *
 * @author Florian Steitz (fst)
 */
fun Element.prependChild(element: Element): Element? {
    return insertAdjacentElement("afterbegin", element)
}

/**
 * Fügt die übergebene [UI-Komponente][SHDUIComponent] via [Element.insertAdjacentElement] an den Anfang der Liste der Kindknoten dieses Elements
 * hinzu und gibt den Wurzelknoten der übergebenen UI-Komponente analog zu [Element.insertAdjacentElement] zurück. Oder `null`, falls die
 * UI-Komponente nicht hinzugefügt werden konnte.
 *
 * @author Florian Steitz (fst)
 */
fun Element.prependChild(component: SHDUIComponent): Element? {
    return prependChild(component.rootNode)
}

/**
 * Gibt die Anzahl an Kindelementen dieses Elements zurück.
 *
 * @see HTMLElement.children
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.getChildCount(): Int {
    return children.length
}

/**
 * Gibt das Kindelement dieses Elements zurück, das sich an der spezifizierten Position befindet. Falls eine ungültige Position angegeben wird, wird
 * `null` zurückgegeben.
 *
 * @see HTMLElement.children
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.getChild(position: Int): Element? {
    return children[position]
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
 * Legt die minimale Breite des Inhaltsbereichs dieses Elements fest.
 *
 * @see CSSStyleDeclaration.width
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withMinWidth(minWidth: Int, unit: CSSUnit): HTMLElement {
    return withMinWidth(minWidth.toDouble(), unit)
}

/**
 * Legt die minimale Breite des Inhaltsbereichs dieses Elements fest.
 *
 * @see CSSStyleDeclaration.width
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withMinWidth(minWidth: Double, unit: CSSUnit): HTMLElement {
    this.style.minWidth = joinValueWithUnit(minWidth, unit)
    return this
}

/**
 * Setzt die Höhe des Inhaltsbereichs dieses Elements auf 100%. Der Inhaltsbereich ist innerhalb des Innenabstands, Rahmens und Außenabstands dieses
 * Elements.
 *
 * @see CSSStyleDeclaration.height
 * @author Florian Steitz (fst)
 */
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
 * Legt die minimale Höhe des Inhaltsbereichs dieses Elements fest.
 *
 * @see CSSStyleDeclaration.minHeight
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withMinHeight(minHeight: Int, unit: CSSUnit): HTMLElement {
    return withMinHeight(minHeight.toDouble(), unit)
}

/**
 * Legt die minimale Höhe des Inhaltsbereichs dieses Elements fest.
 *
 * @see CSSStyleDeclaration.minHeight
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withMinHeight(minHeight: Double, unit: CSSUnit): HTMLElement {
    this.style.minHeight = joinValueWithUnit(minHeight, unit)
    return this
}

/**
 * Legt die line-height dieses Elements fest.
 *
 * @see CSSStyleDeclaration.lineHeight
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withLineHeight(lineHeight: Int, unit: CSSUnit): HTMLElement {
    return withLineHeight(lineHeight.toDouble(), unit)
}

/**
 * Legt die line-height dieses Elements fest.
 *
 * @see CSSStyleDeclaration.lineHeight
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withLineHeight(lineHeight: Double, unit: CSSUnit): HTMLElement {
    this.style.lineHeight = joinValueWithUnit(lineHeight, unit)
    return this
}

/**
 * Legt die font-size dieses Elements fest.
 *
 * @see CSSStyleDeclaration.fontSize
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withFontSize(fontSize: Double, unit: CSSUnit): HTMLElement {
    this.style.fontSize = joinValueWithUnit(fontSize, unit)
    return this
}

/**
 * Legt die font-size dieses Elements fest.
 *
 * @see CSSStyleDeclaration.fontSize
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withFontSize(fontSize: Int, unit: CSSUnit): HTMLElement {
    return withFontSize(fontSize.toDouble(), unit)
}

/**
 * Legt die font-weight dieses Elements fest.
 *
 * @see CSSStyleDeclaration.fontWeight
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withFontWeight(fontWeight: Int): HTMLElement {
    this.style.fontWeight = fontWeight.toString()
    return this
}

/**
 * Legt die Textfarbe dieses Elements fest.
 *
 * @see CSSStyleDeclaration.color
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withColor(color: String): HTMLElement {
    this.style.color = color
    return this
}

/**
 * Legt den text-align dieses Elements fest.
 *
 * @see CSSStyleDeclaration.lineHeight
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withTextAlign(textAlign: CSSTextAlign): HTMLElement {
    this.style.textAlign = textAlign.value
    return this
}

/**
 * Legt die Innenabstände aller vier Seiten dieses Elements fest. Der Innenabstand ist der Bereich zwischen dem Inhalt und dem Rahmen.
 *
 * @see CSSStyleDeclaration.padding
 * @author Florian Steitz (fst)
 */
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
 * Legt den oberen Außenabstand dieses Elements auf "auto".
 *
 * @see CSSStyleDeclaration.marginBottom
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withMarginTopAuto(): HTMLElement {
    this.style.marginTop = CSSKeyword.AUTO.value
    return this
}

/**
 * Legt den rechten Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginRight
 * @author Florian Steitz (fst)
 */
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
 * Legt den unteren Außenabstand dieses Elements auf "auto".
 *
 * @see CSSStyleDeclaration.marginBottom
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withMarginBottomAuto(): HTMLElement {
    this.style.marginBottom = CSSKeyword.AUTO.value
    return this
}

/**
 * Legt den linken Außenabstand dieses Elements fest.
 *
 * @see CSSStyleDeclaration.marginLeft
 * @author Florian Steitz (fst)
 */
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
fun HTMLElement.withVisible(visible: Boolean): HTMLElement {
    withDisplay(if (visible) EMPTY_STRING else CSSDisplay.NONE.value)
    return this
}

/**
 * Legt den Wert der Eigenschaft `display` dieses Elements fest.
 *
 * @author Florian Steitz (fst)
 */
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
fun HTMLElement.withStyleNames(vararg styleNames: String): HTMLElement {
    classList.add(*styleNames)
    return this
}

/**
 * Entfernt den übergebenen, sog. "Style-Namen" von diesem Element. Bei einem Style-Namen handelt es sich technisch um eine sog. CSS-Klasse.
 *
 * In CSS-Dateien muss ein Punkt vor dem Namen jeder CSS-Klasse sein. Dieser Punkt darf allerdings nicht an diese Methode übergeben werden. D.h. es
 * wird nur der eigentliche Name der CSS-Klasse übergeben.
 *
 * @see HTMLElement.classList
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.removeStyleName(styleName: String): HTMLElement {
    this.removeClass(styleName)
    return this
}

/**
 * Fügt den übergebenen, sog. "Style-Namen" zu diesem Element hinzu oder entfernt ihn, je nachdem ob er bereits hinzugefügt wurde oder nicht . Bei
 * einem Style-Namen handelt es sich technisch um eine sog. CSS-Klasse. Über CSS-Klassen kann u.a. die Darstellung dieses Elements geändert bzw.
 * beeinflusst werden.
 *
 * In CSS-Dateien muss ein Punkt vor dem Namen jeder CSS-Klasse sein. Dieser Punkt darf allerdings nicht an diese Methode übergeben werden. D.h. es
 * wird nur der eigentliche Name der CSS-Klasse übergeben.
 *
 * @see HTMLElement.classList
 * @see DOMTokenList.toggle
 * @see [HTMLElement.withStyleName]
 * @author Tobias Isekeit (ist), Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.toggleStyleName(styleName: String): HTMLElement {
    this.classList.toggle(styleName)
    return this
}

/**
 * Fügt den übergebenen, sog. "Style-Namen" zu diesem Element hinzu, wenn `true` übergeben wird oder entfernt ihn, wenn `false` übergeben wird.
 *
 * Bei einem Style-Namen handelt es sich technisch um eine sog. CSS-Klasse. Über CSS-Klassen kann u.a. die Darstellung dieses Elements geändert bzw.
 * beeinflusst werden.
 *
 * In CSS-Dateien muss ein Punkt vor dem Namen jeder CSS-Klasse sein. Dieser Punkt darf allerdings nicht an diese Methode übergeben werden. D.h. es
 * wird nur der eigentliche Name der CSS-Klasse übergeben.
 *
 * @see HTMLElement.classList
 * @see DOMTokenList.toggle
 * @see [HTMLElement.withStyleName]
 * @author Tobias Isekeit (ist), Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.toggleStyleName(styleName: String, add: Boolean): HTMLElement {
    this.classList.toggle(styleName, add)
    return this
}

/**
 * Entfernt alle CSS Klassen und leert das Style Attribut
 *
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.clearStyle(): HTMLElement {
    style.cssText = EMPTY_STRING
    return this
}

/**
 * Entfernt alle CSS Klassen
 *
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.clearStyleNames(): HTMLElement {
    className = EMPTY_STRING
    return this
}

/**
 * Entfernt CSS Klassen.
 *
 * Für ausführliche Dokumentation siehe [withStyleNames].
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.removeStyleNames(vararg styleNames: String): HTMLElement {
    this.removeClass(*styleNames)
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
fun HTMLElement.withClickListener(listener: (Event) -> Unit): HTMLElement {
    addClickListener(listener)
    return this
}

/**
 * Ermöglicht eine "fließende" Konfiguration der [CSS-Regeln][CSSStyleDeclaration] dieses Elements über die übergebene Funktion, indem dessen Property
 * [style][HTMLElement.style] als Receiver an diese Funktion übergeben wird.
 *
 * @author Florian Steitz (fst)
 */
fun HTMLElement.withStyle(configure: CSSStyleDeclaration.() -> Unit): HTMLElement {
    configure(style)
    return this
}

/**
 * Gibt den [CSSDisplay]-Wert zurück, der diesem Element zugewiesen wurde. Ein solcher Enum-Wert gilt als zugewiesen, wenn der Eigenschaft `display`
 * dieses Elements ein Wert zugewiesen wurde. Dies kann sowohl programmatisch als auch via CSS geschehen sein.
 *
 * Falls kein [CSSDisplay]-Wert ermittelt werden konnte, wird `null` zurückgegeben. Dieser Fall kann bspw. eintreten, wenn die Eigenschaft `display`
 * nicht gesetzt ist oder wenn diese Eigenschaft einen Wert enthält, für den im Enum [CSSDisplay] kein Äquivalent existiert.
 *
 * @see [CSSDisplay.findByStyleValue]
 * @author Florian Steitz (fst)
 */
fun HTMLElement.getDisplay(): CSSDisplay? {
    if (style.display.isBlank()) {
        return null
    }

    return CSSDisplay.findByStyleValue(style.display)
}

/**
 * Gibt `true` zurück, wenn dieses Element sichtbar ist. Andernfalls `false`.
 *
 * Ein Element gilt als sichtbar, wenn dessen Methode [getDisplay] nicht [CSSDisplay.NONE] zurückgibt.
 *
 * @author Florian Steitz (fst)
 */
fun HTMLElement.isVisible(): Boolean {
    val isDisplayNone = getDisplay()?.equals(CSSDisplay.NONE) ?: false
    return !isDisplayNone
}

/**
 * Generiert via [generateIDFor] eine eindeutige ID für dieses HTML-Element, das anschließend u.a. an das Property [HTMLElement.id] dieses
 * HTML-Elements übergeben werden kann.
 *
 * @return Die generierte ID.
 */
@Suppress("unused")
fun HTMLTag.generateID(): String {
    return generateIDFor(tagName)
}

/**
 * Setzt die Höhe und die Breite des Inhaltsbereichs dieses Elements auf 100%. Der Inhaltsbereich ist innerhalb des Innenabstands, Rahmens und
 * Außenabstands dieses Elements.
 *
 * @see [withFullWidth]
 * @see [withFullHeight]
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLElement.withSizeFull(): HTMLElement {
    return this.withFullWidth().withFullHeight()
}

/**
 * Setzt die Höhe und Breite des Elements auf 'unset'.
 *
 * @see [withWidthUndefined]
 * @see [withHeightUndefined]
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.withSizeUndefined(): HTMLElement {
    return this.withWidthUndefined().withHeightUndefined()
}

/**
 * Setzt die Breite des Elements auf [CSSKeyword.UNSET].
 *
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withWidthUndefined(): HTMLElement {
    this.style.width = CSSKeyword.UNSET.value
    return this
}

/**
 * Setzt die Höhe des Elements auf [CSSKeyword.UNSET].
 *
 * @author Marcel Ziganow (zim)
 */
fun HTMLElement.withHeightUndefined(): HTMLElement {
    this.style.height = CSSKeyword.UNSET.value
    return this
}

/**
 * Setzt den Außenabstand auf 0
 *
 * @see CSSStyleDeclaration.margin
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.withZeroMargin(): HTMLElement {
    this.style.margin = "0"
    return this
}

/**
 * Setzt den oberen Außenabstand auf 0
 *
 * @see CSSStyleDeclaration.marginTop
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.withZeroMarginTop(): HTMLElement {
    this.style.marginTop = "0"
    return this
}

/**
 * Setzt den rechten Außenabstand auf 0
 *
 * @see CSSStyleDeclaration.marginRight
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.withZeroMarginRight(): HTMLElement {
    this.style.marginRight = "0"
    return this
}

/**
 * Setzt den unteren Außenabstand auf 0
 *
 * @see CSSStyleDeclaration.marginBottom
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.withZeroMarginBottom(): HTMLElement {
    this.style.marginBottom = "0"
    return this
}

/**
 * Setzt den linken Außenabstand auf 0
 *
 * @see CSSStyleDeclaration.marginLeft
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.withZeroMarginLeft(): HTMLElement {
    this.style.marginLeft = "0"
    return this
}

/**
 * Setzt den overflow-x und overflow-y auf `hidden`
 *
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun HTMLElement.withHiddenOverflow(): HTMLElement {
    this.style.overflowX = "hidden"
    this.style.overflowY = this.style.overflowX
    return this
}

/**
 * Legt fest, ob der Wert dieses Eingabeelements änderbar sein soll oder nicht.
 *
 * @see HTMLInputElement.readOnly
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLInputElement.withReadOnly(readOnly: Boolean): HTMLInputElement {
    this.readOnly = readOnly
    return this
}

/**
 * Legt fest, ob dieses Eingabeelement aktiviert sein soll oder nicht.
 *
 * @see HTMLInputElement.disabled
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLInputElement.withEnabled(enabled: Boolean): HTMLInputElement {
    this.disabled = !enabled
    return this
}

/**
 * Leert dieses Eingabeelement, indem dessen aktueller Wert mit [EMPTY_STRING] überschrieben wird.
 *
 * @see HTMLInputElement.disabled
 * @author Tobias Isekeit (ist), Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLInputElement.clear(): HTMLInputElement {
    this.value = EMPTY_STRING
    return this
}

/**
 * Legt fest, ob der Wert dieses Textfelds änderbar sein soll oder nicht.
 *
 * @see HTMLTextAreaElement.readOnly
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLTextAreaElement.withReadOnly(readOnly: Boolean): HTMLTextAreaElement {
    this.readOnly = readOnly
    return this
}

/**
 * Legt fest, ob dieses Textfeld aktiviert sein soll oder nicht.
 *
 * @see HTMLTextAreaElement.disabled
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLTextAreaElement.withEnabled(enabled: Boolean): HTMLTextAreaElement {
    this.disabled = !enabled
    return this
}

/**
 * Leert dieses Textfeld, indem dessen aktueller Wert mit [EMPTY_STRING] überschrieben wird.
 *
 * @see HTMLTextAreaElement.disabled
 * @author Tobias Isekeit (ist), Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLTextAreaElement.clear(): HTMLTextAreaElement {
    this.value = EMPTY_STRING
    return this
}

/**
 * Legt fest, ob dieses Button-Element aktiviert sein soll oder nicht.
 *
 * @see HTMLButtonElement.disabled
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLButtonElement.withEnabled(enabled: Boolean): HTMLButtonElement {
    this.disabled = !enabled
    return this
}

/**
 * Selektiert den ersten Wert dieses Elements.
 *
 * @see HTMLSelectElement.selectedIndex
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLSelectElement.selectFirst(): HTMLSelectElement {
    this.selectedIndex = 0
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