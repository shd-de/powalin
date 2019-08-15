package de.shd.basis.kotlin.ui.css

/**
 * The `float` property specifies how an element should float.
 *
 * @author Marcel Ziganow (zim)
 */
enum class CSSFloat(internal val value: String) {

  /**
   * 	The element floats to the left of its container
   */
  LEFT("left"),

  /**
   * 	The element floats the right of its container
   */
  RIGHT("right"),

  /**
   * The element does not float, (will be displayed just where it occurs in the text). This is default
   */
  NONE("none")
}