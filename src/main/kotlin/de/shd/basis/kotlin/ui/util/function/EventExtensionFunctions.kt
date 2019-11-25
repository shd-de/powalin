package de.shd.basis.kotlin.ui.util.function

import org.w3c.dom.events.KeyboardEvent

/**
 * Prüft, ob es sich bei diesem [KeyboardEvent] um ein sog. "Enter-Event" handelt. Dies ist der Fall, wenn es als [keyCode][KeyboardEvent.keyCode] die
 * Zahl `13` zurück gibt.
 *
 * @author Tobias Isekeit (ist), Florian Steitz (fst)
 */
fun KeyboardEvent.isEnterEvent(): Boolean {
    return this.keyCode == 13
}

/**
 * Prüft, ob es sich bei diesem [KeyboardEvent] um ein sog. "Tab-Event" handelt. Dies ist der Fall, wenn es als [keyCode][KeyboardEvent.keyCode] die
 * Zahl `9` zurück gibt.
 *
 * @author Tobias Isekeit (ist), Florian Steitz (fst)
 */
fun KeyboardEvent.isTabEvent(): Boolean {
    return this.keyCode == 9
}