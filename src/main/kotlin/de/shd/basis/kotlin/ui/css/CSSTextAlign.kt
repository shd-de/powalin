package de.shd.basis.kotlin.ui.css

/**
 * Enthält die Keywords, welche als Wert für `text-align` verwendet werden können
 *
 * @author Marcel Ziganow (zim)
 */
enum class CSSTextAlign(internal val value: String) {

  /**
   * Keyword für das Alignment `left`
   */
  LEFT("left"),

  /**
   * Keyword für das Alignment `right`
   */
  RIGHT("right"),

  /**
   * Keyword für das Alignment `center`
   */
  CENTER("center"),

  /**
   * Keyword um das Alignment zurückzusetzen
   */
  UNSET("unset")
}