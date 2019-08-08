package de.shd.basis.kotlin.ui.util.sort

import kotlin.math.min

/**
 * Comparator der Alphanumerisch Strings sortiert.
 * <p>
 * Zuerst wird der String in Numerische- und Alphabetische-Teile aufgeteilt <br/>
 * Beispiel: <b>123ABCDE567FGH</b> wird zur Liste <b>{"123", "ABCDE", "567", "FGH"}</b>
 * <p>
 * Dann werden beide Strings mit folgender Vorgehensweise miteinander verglichen:
 * <ol>
 * <li>Sind beide Teile numerische, dann werden diese als Zahlen verglichen</li>
 * <li>Sind die Zahlen gleichwertig, dann werden die Teile als String verglichen</li>
 * <li>Sind die Strings gleichwertig, dann werden die naechsten Teile geprueft</li>
 * <li>Sind die Teile Strings, dann werden diese als String verglichen</li>
 * <li>Sind die Strings gleichwertig, dann werden die naechsten Teile geprueft</li>
 * <li>Sind keine Teile mehr vorhanden, dann wird der String mit den geringeren Teilen favorisiert</li>
 * </ol>
 *
 * @author Tobias Isekeit (ist)
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
object AlphanumericSorting : Comparator<String> {

    /**
     * Beispiel einer Sortierung: <br/>
     * <b>unsortiert:</b><br/>
     * 1 <br/>
     * 01 <br/>
     * ABC <br/>
     * 1ABC <br/>
     * 1 ABC <br/>
     * 001 <br/>
     * 5 <br/>
     * <b>sortiert:</b><br/>
     * 001 <br/>
     * 01 <br/>
     * 1 <br/>
     * 1ABC <br/>
     * 1 ABC <br/>
     * 5 <br/>
     * ABC <br/>
     * <p></p>
     * {@inheritDoc}
     */
    override fun compare(a: String, b: String): Int {
        // Container-Objekt fuer die zu vergleichenden Strings erzeugen
        val parts1: MutableList<ElementPart> = collectParts(a)
        val parts2: MutableList<ElementPart> = collectParts(b)

        // Iteriere ueber die Parts der beiden Elemente
        return if (parts1.size > 1 || parts2.size > 1) {
            checkElements(parts1, parts2)
        } else {
            val partElement1 = parts1[0]
            val partElement2 = parts2[0]
            checkElementParts(partElement1, partElement2)
        }
    }

    /**
     * Prueft die Wertigkeit der beiden Elemente anhand der einzelnen Parts, die diese besitzen
     */
    private fun checkElements(parts1: MutableList<ElementPart>, parts2: MutableList<ElementPart>): Int {
        var result = 0
        val maxSize = min(parts1.size, parts2.size)
        for (i in 0 until maxSize) {
            val partElement1 = parts1[i]
            val partElement2 = parts2[i]
            result = checkElementParts(partElement1, partElement2)

            if (result != 0) {
                break
            }
        }

        // Haben die Elemente nach allen Pruefungen immernoch die gleiche Wertigkeit, so wird das Elemente mit den
        // geringeren Parts favorisiert
        if (result == 0) {
            result = if (parts1.size > parts2.size) 1 else -1
        }
        return result
    }

    /**
     * Vergleicht zwei Parts miteinander um deren Wertigkeit zueinander festzustellen
     */
    private fun checkElementParts(o1: ElementPart, o2: ElementPart): Int {
        var result: Int
        // Zahlen werden numerisch geprueft und Strings werden alphabetisch geprueft
        if (o1.isDigit && o2.isDigit) {
            result = o1.value.toInt().compareTo(o2.value.toInt())

            if (result == 0) {
                result = o1.value.compareTo(o2.value)
            }
        } else {
            result = o1.value.compareTo(o2.value)
        }
        return result
    }

    /**
     * Sammelt alle Numerischen- und Alphabetischen-Teile des Strings und hinterlegt diese in einer <code>parts</code>-Liste
     */
    private fun collectParts(value: String): MutableList<ElementPart> {
        val parts = mutableListOf<ElementPart>()
        collectParts(value, parts)
        return parts
    }

    /**
     * Sammelt alle Numerischen- und Alphabetischen-Teile des Strings und hinterlegt diese in der übergebenen <code>parts</code>-Liste
     */
    private fun collectParts(value: String, parts: MutableList<ElementPart>) {
        val isFirstRun = parts.isEmpty()
        val chars = CharArray(value.length)
        for (i in 0 until chars.size) {
            val ch = value[i]
            if (i == 0 || ch.isDigit() == chars[0].isDigit()) {
                chars[i] = ch
            } else {
                parts.addPart(chars)
                collectParts(value.substring(i), parts)
                break
            }
        }

        if (isFirstRun || String(chars).length == value.length) {
            parts.addPart(chars)
        }
    }

    /**
     * Fuegt ein {@code char[]} als neuen {@link ElementPart} der <code>parts</code>-Liste hinzu
     */
    private fun MutableList<ElementPart>.addPart(chars: CharArray) = this.add(ElementPart(chars))

    /**
     * Container-Element fuer den einzelnen Part
     */
    private class ElementPart(chars: CharArray) {
        val value: String = String(chars).trim { it <= ' ' }
        val isDigit: Boolean = chars[0].isDigit()
    }
}

internal fun Char.isDigit(): Boolean = String(CharArray(1) { this }).toIntOrNull() != null