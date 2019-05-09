package de.shd.basis.kotlin.ui.mvc.view

import org.w3c.dom.Node

/**
 * Die [View](https://de.wikipedia.org/wiki/Model_View_Controller#Pr%C3%A4sentation_(view)) einer Komponente, die die Darstellungsschicht einer
 * Komponente enthält.
 *
 * Eine View soll niemals ihre Struktur zurückgeben und stattdessen eine einheitliche API zur Manipulation bzw. Konfiguration dieser zur Verfügung
 * stellen.
 *
 * @author Florian Steitz (fst)
 */
interface MVCView {

    /**
     * Der Wurzel-[Node] dieser View (und damit dieser Komponente), der zu anderen Views als Kind-Knoten hinzugefügt wird. Alle View-spezifischen
     * Knoten müssen sich dabei unterhalb dieses Wurzel-Knotens befinden.
     */
    val rootNode: Node

    /**
     * Initialisiert diese View, indem bspw. Standard-[Nodes][Node] zum [rootNode] dieser View hinzugefügt werden und das grundlegende Styling dieser
     * View definiert bzw. angepasst wird.
     */
    fun init()
}