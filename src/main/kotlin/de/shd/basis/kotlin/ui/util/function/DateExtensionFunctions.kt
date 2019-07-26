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
    val mapper = DateStringMapper(this)

    return "${mapper.year}-${mapper.month}-${mapper.day}T${mapper.hours}:${mapper.minutes}:${mapper.seconds}"
}

/**
 * Konvertiert dieses [Date] in das DateFormat "dd.MM.yyyy"
 */
@Suppress("unused")
fun Date.toDayMonthYear(): String {
    val mapper = DateStringMapper(this)

    return "${mapper.day}.${mapper.month}.${mapper.year}"
}

/**
 * Konvertiert dieses [Date] in das DateFormat "dd.MM.yyyy HH:mm"
 */
@Suppress("unused")
fun Date.toDayMonthYearHourMinutes(): String {
    val mapper = DateStringMapper(this)

    return "${mapper.day}.${mapper.month}.${mapper.year} ${mapper.hours}:${mapper.minutes}"
}

/**
 * Mapped ein [Date] und seine Information auf Strings um und stellt dabei sicher, das die Werte immer zwei Ziffern haben
 *
 * @author Tobias Isekeit (ist)
 */
private class DateStringMapper(dateToMap: Date) {
    val date: Date = dateToMap
    val year: String = date.getFullYear().toString()
    val month: String = ensureTwoDigits(date.getMonth() + 1) // Die Monate müssen ab 1 und nicht ab 0 beginnen.
    val day: String = ensureTwoDigits(date.getDay())
    val hours: String = ensureTwoDigits(date.getHours())
    val minutes: String = ensureTwoDigits(date.getMinutes())
    val seconds: String = ensureTwoDigits(date.getSeconds())
}

/**
 * Konvertiert die übergebene Nummer in einen String und stellt sicher, dass die zurückgegebene, textuelle Nummer mindestens zwei Stellen hat.
 *
 * @author Florian Steitz (fst)
 */
private fun ensureTwoDigits(number: Int): String {
    return number.toString().padStart(2, '0')
}