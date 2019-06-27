package de.shd.basis.kotlin.ui.util.function

import org.w3c.dom.HTMLInputElement
import kotlin.js.Date

/**
 * Konvertiert dieses [Date] in ein ISO-8601-Format mit Tageszeit, das vom [HTMLInputElement] mit dem Typ `datetime-local` als Wert akzeptiert wird.
 *
 * @author Marcel Ziganow (zim), Florian Steitz (fst)
 */
@Suppress("unused")
fun Date.toDateTimeLocal(): String {
    val fullYear = getFullYear()
    val paddedMonth = ensureTwoDigits(getMonth() + 1) // Der Monat muss mit 1 und nicht mit 0 beginnen.
    val paddedDay = ensureTwoDigits(getDate())
    val paddedHours = ensureTwoDigits(getHours())
    val paddedMinutes = ensureTwoDigits(getMinutes())
    val paddedSeconds = ensureTwoDigits(getSeconds())

    return "$fullYear-$paddedMonth-${paddedDay}T$paddedHours:$paddedMinutes:$paddedSeconds"
}

/**
 * Konvertiert die übergebene Nummer in einen String und stellt sicher, dass die zurückgegebene, textuelle Nummer mindestens zwei Stellen hat.
 *
 * @author Florian Steitz (fst)
 */
private fun ensureTwoDigits(number: Int): String {
    return number.toString().padStart(2, '0')
}