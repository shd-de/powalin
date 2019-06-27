package de.shd.basis.kotlin.ui.util.function

import kotlin.js.Date

/**
 * Erzeugt aus einem Date ein DateTimeLocal für ein [org.w3c.dom.HTMLInputElement] vom Typ datetime-local
 *
 * @author Marcel Ziganow (zim)
 */
fun Date.toDateTimeLocal(): String {
  val date = this
  val ten = fun(i: Int): String {
    return (if (i < 10) "0" else "") + i
  }
  val YYYY = date.getFullYear()
  val MM = ten(date.getMonth() + 1)
  val DD = ten(date.getDate())
  val HH = ten(date.getHours())
  val II = ten(date.getMinutes())
  val SS = ten(date.getSeconds())
  return "${YYYY}-${MM}-${DD}T${HH}:${II}:${SS}"
}