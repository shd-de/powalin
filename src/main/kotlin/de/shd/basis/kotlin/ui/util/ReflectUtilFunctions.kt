package de.shd.basis.kotlin.ui.util

import kotlin.reflect.KClass

/**
 * Erzeugt dynamisch eine neue Instanz der übergebenen Klasse.
 *
 * Diese Funktion ist notwendig, da das JavaScript-Modul von Kotlin derzeit keine entsprechende Reflection-API zur Verfügung stellt.
 *
 * @author Florian Steitz (fst)
 */
fun <INSTANCE : Any> createInstance(klass: KClass<INSTANCE>): INSTANCE {
    return createInstance(klass.js)
}

/**
 * Erzeugt dynamisch eine neue Instanz einer Klasse auf Basis des übergebenen, zur Klasse gehörenden JavaScript-Konstruktors.
 *
 * Die tatsächliche Erzeugung der Instanz übernimmt die gleichnamige Funktion, die im Skript `shd-basis-util.js` definiert ist. Diese Methode soll von
 * Anwendungen nicht direkt verwendet werden (können).
 *
 * @param <INSTANCE>
 * @see JsClass
 * @author Florian Steitz (fst)
 */
internal external fun <INSTANCE : Any> createInstance(jsClass: JsClass<INSTANCE>): INSTANCE