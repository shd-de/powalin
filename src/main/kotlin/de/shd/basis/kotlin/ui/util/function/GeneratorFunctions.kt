package de.shd.basis.kotlin.ui.util.function

import de.shd.basis.kotlin.ui.component.SHDUIComponent
import de.shd.basis.kotlin.ui.util.constant.DASH
import org.w3c.dom.HTMLElement
import kotlin.random.Random

/**
 * Generiert eine eindeutige ID für die übergebene Komponente, die anschließend u.a. an die Methode [withID] der Komponente übergeben werden kann.
 *
 * **Hierüber generierte IDs sind wie folgt aufgebaut:**
 *
 * _${Name des Wurzelknotens}-${Zufällige [Base36](https://en.wikipedia.org/wiki/Base36)-Zeichenkette}_
 *
 * @param component Eine Komponente, für die eine eindeutige ID generiert werden soll.
 * @return Die generierte ID.
 */
@Suppress("unused")
fun generateIDFor(component: SHDUIComponent): String {
    return generateIDFor(component.rootNode)
}

/**
 * Generiert eine eindeutige ID für das übergebene HTML-Element, die anschließend u.a. an die Methode [withID] des HTML-Elements übergeben werden kann.
 *
 * **Hierüber generierte IDs sind wie folgt aufgebaut:**
 *
 * _${Name des HTML-Elements}-${Zufällige [Base36](https://en.wikipedia.org/wiki/Base36)-Zeichenkette}_
 *
 * @param element Ein HTML-Element, für das eine eindeutige ID generiert werden soll.
 * @return Die generierte ID.
 */
fun generateIDFor(element: HTMLElement): String {
    return generateIDFor(element.nodeName)
}

/**
 * Generiert eine eindeutige ID für ein HTML-Element mit dem übergebenen Namen, die anschließend u.a. an die Methode [withID] des HTML-Elements
 * übergeben werden kann.
 *
 * **Hierüber generierte IDs sind wie folgt aufgebaut:**
 *
 * _${Name des HTML-Elements}-${Zufällige [Base36](https://en.wikipedia.org/wiki/Base36)-Zeichenkette}_
 *
 * @param elementName Der Name eines HTML-Elements, für das eine eindeutige ID generiert werden soll.
 * @return Die generierte ID.
 */
fun generateIDFor(elementName: String): String {
    return elementName + DASH + Random.nextLong(0, Long.MAX_VALUE).toString(36)
}