package de.shd.basis.kotlin.ui.mvc.view

import de.shd.basis.kotlin.ui.component.SHDFluentUIComponent
import de.shd.basis.kotlin.ui.component.SHDUIComponent
import org.w3c.dom.Node

/**
 * Die [View](https://de.wikipedia.org/wiki/Model_View_Controller#Pr%C3%A4sentation_(view)) einer MVC-Komponente, die dessen Darstellungsschicht
 * enthält.
 *
 * Eine View soll niemals ihre Kindelemente bzw. Kindkomponenten zurückgeben und stattdessen eine einheitliche API zur Manipulation bzw. Konfiguration
 * ihrer Struktur zur Verfügung stellen.
 *
 * Dieses Interface ist eine Spezialisierung von [SHDUIComponent] und daher können ihre Implementierungen als reguläre UI-Komponenten verwendet werden.
 *
 * @author Florian Steitz (fst)
 */
interface MVCView : SHDFluentUIComponent<MVCView> {

    /**
     * Initialisiert diese View, indem bspw. Standard-[Nodes][Node] zum [rootNode] dieser View hinzugefügt werden und das grundlegende Styling dieser
     * View definiert bzw. angepasst wird.
     */
    fun init()
}