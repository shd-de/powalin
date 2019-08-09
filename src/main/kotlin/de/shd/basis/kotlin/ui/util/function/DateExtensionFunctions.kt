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
    val dateFields = DateFields(this)
    return "${dateFields.year}-${dateFields.month}-${dateFields.day}T${dateFields.hours}:${dateFields.minutes}:${dateFields.seconds}"
}

/**
 * Konvertiert dieses [Date] in ein ISO-8601-Format ohne Tageszeit. Dies lässt sich in Java als LocalDate deserialisieren
 *
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
fun Date.toLocalDate(): String {
    val dateFields = DateFields(this)
    return "${dateFields.year}-${dateFields.month}-${dateFields.day}"
}

/**
 * Konvertiert dieses [Date] in das DateFormat "dd.MM.yyyy"
 *
 * @author Tobias Isekeit (ist)
 */
@Suppress("unused")
fun Date.toDayMonthYear(): String {
    val dateFields = DateFields(this)
    return "${dateFields.day}.${dateFields.month}.${dateFields.year}"
}

/**
 * Konvertiert dieses [Date] in das DateFormat "dd.MM.yyyy HH:mm"
 *
 * @author Tobias Isekeit (ist)
 */
@Suppress("unused")
fun Date.toDayMonthYearHourMinutes(): String {
    val dateFields = DateFields(this)
    return "${dateFields.day}.${dateFields.month}.${dateFields.year} ${dateFields.hours}:${dateFields.minutes}"
}

/**
 * Konvertiert dieses [Date] in das DateFormat "dd.MM.yyyy HH:mm:ss"
 *
 * @author Tobias Isekeit (ist)
 */
@Suppress("unused")
fun Date.toDayMonthYearHourMinutesSeconds(): String {
    val dateFields = DateFields(this)
    return "${dateFields.day}.${dateFields.month}.${dateFields.year} ${dateFields.hours}:${dateFields.minutes}:${dateFields.seconds}"
}

/**
 * Mapped ein [Date] und seine Information auf Strings um und stellt dabei sicher, das die Werte immer zwei Ziffern haben
 *
 * @author Tobias Isekeit (ist), Florian Steitz (fst)
 */
private class DateFields(date: Date) {
    val year: String = date.getFullYear().toString()
    val month: String = ensureTwoDigits(date.getMonth() + 1) // Die Monate müssen ab 1 und nicht ab 0 beginnen.
    val day: String = ensureTwoDigits(date.getDate())
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