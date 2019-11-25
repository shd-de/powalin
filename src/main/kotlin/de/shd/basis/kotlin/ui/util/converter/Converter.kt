package de.shd.basis.kotlin.ui.util.converter

import de.shd.basis.kotlin.ui.util.constant.EMPTY_STRING

/**
 * Standard-Interface für Konvertierer, die einen Wert von einem Datentypen in einen entsprechenden Wert von einem anderen Datentypen konvertieren.
 *
 * @author Florian Steitz (fst)
 */
interface Converter<SOURCE_TYPE, TARGET_TYPE> {

    /**
     * Konvertiert den übergebenen Wert und gibt das Ergebnis zurück.
     */
    fun convert(value: SOURCE_TYPE?): TARGET_TYPE
}

/**
 * Eine Implementierung von [Converter], die ein übergebenes Objekt in einen [String] konvertiert, indem dessen [toString][Any.toString]-Methode
 * aufgerufen und das Ergebnis zurückgegeben wird. Falls `null` übergeben wird, wird [EMPTY_STRING] zurückgegeben.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
object ToStringConverter : Converter<Any, String> {
    override fun convert(value: Any?): String {
        return value?.toString() ?: EMPTY_STRING
    }
}

/**
 * Eine Implementierung von [Converter], die unabhängig vom übergebenen Objekt immer [EMPTY_STRING] zurück gibt.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
object ToEmptyStringConverter : Converter<Any, String> {
    override fun convert(value: Any?): String {
        return EMPTY_STRING
    }
}