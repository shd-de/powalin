package de.shd.basis.kotlin.ui.mvc.controller

import de.shd.basis.kotlin.ui.mvc.view.MVCView
import de.shd.basis.kotlin.ui.state.AppStateManager

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
 * Implementierungen von diesem Interface sollen ausschließlich via [MVCControllerFactory.create] instanziiert werden.
 *
 * @author Florian Steitz (fst)
 */
interface MVCController<VIEW : MVCView> {

    /**
     * Die zugehörige [MVCView] einer Komponente, die die Darstellungsschicht einer Komponente enthält. Fremde Controller sollen niemals mit ihr
     * direkt interagieren.
     */
    val view: VIEW

    /**
     * Initialisiert diesen Controller, indem u.a. die verwaltete View erzeugt und initialisiert wird. Darüber hinaus wird im Regelfall auch die
     * Verbindung zwischen diesem Controller und der View initialisiert, damit dieser Controller die Steuerung der View übernehmen kann (wie z.B. das
     * Registrieren von Klick-Listenern, damit dieser Controller auf Klicks reagieren kann).
     */
    fun init()

    /**
     * Diese Methode kann vom implementierenden Controller überschrieben werden, um die Daten und/oder die View, die er verwaltet, automatisch zu
     * aktualisieren, wenn dessen View via [AppStateManager.openState] oder [AppStateManager.openAsNewState] als Wurzelknoten in den DOM-Baum des
     * Frameworks eingehängt wird.
     *
     * **Wichtig:**
     *
     * Diese Methode soll nicht manuell innerhalb der Anwendung aufgerufen werden.
     */
    fun onEnter() {
        // Standardmäßig tut diese Methode nichts.
    }
}