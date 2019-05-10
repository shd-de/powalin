package de.shd.basis.kotlin.ui.mvc.controller

import de.shd.basis.kotlin.ui.mvc.view.MVCView

/**
 * Die abstrakte Standard-Implementierung von [MVCController], die u.a. die Verwaltung und Initialisierung der zugehörigen [MVCView] übernimmt.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
abstract class AbstractMVCController<VIEW : MVCView> : MVCController<VIEW> {

    final override val view: VIEW by lazy {
        createView()
    }

    final override fun init() {
        view.init()
        afterInit()
    }

    /**
     * Ableitende Controller können diese Methode optional überschreiben, um Logiken auszuführen, die nach der Initialisierung der zugehörigen
     * [MVCView] ausgeführt werden sollen. Dazu gehört im Regelfall u.a. das Registrieren von Event-Listenern (wie z.B. Klick-Listener).
     */
    protected open fun afterInit() {
        // Standardmäßig tut diese Methode nichs.
    }

    /**
     * Instanziiert die View, die von diesem Controller verwaltet werden soll. Diese Methode soll niemals manuell aufgerufen werden, weil sich diese
     * abstrakte Implementierung von [MVCController] (und damit das Framework) darum kümmert. Außerdem soll ein Controller stets exakt eine Instanz
     * seiner View halten und diese Instanz niemals austauschen.
     */
    protected abstract fun createView(): VIEW
}