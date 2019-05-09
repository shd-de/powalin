package de.shd.basis.kotlin.ui.mvc.controller

import de.shd.basis.kotlin.ui.mvc.view.MVCView

/**
 * Der [Controller](https://de.wikipedia.org/wiki/Model_View_Controller#Steuerung_(controller)) einer Komponente, der die Steuerung und Verwaltung der
 * zugehörigen [MVCView] übernimmt.
 *
 * Er entscheidet u.a. was bei Benutzerinteraktionen konkret geschieht und enthält oftmals auch Teile der Komponenten-spezifischen Geschäftslogik.
 * Generell sollen aber allgemeine Geschäftslogiken entweder in einen separaten Service oder in ein Backend ausgelagert werden.
 *
 * Darüber hinaus stellt der Controller die öffentliche API einer Komponente dar, mit der andere Komponenten interagieren sollen. Ein Controller soll
 * niemals mit der View eines anderen Controllers direkt interagieren.
 *
 * @author Florian Steitz (fst)
 */
interface MVCController<VIEW : MVCView> {

    /**
     * Die zugehörige [MVCView] einer Komponente, die die Darstellungsschicht einer Komponente enthält. Fremde Controller sollen niemals mit ihr
     * direkt interagieren.
     */
    val view: VIEW
}