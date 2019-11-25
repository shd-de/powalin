package de.shd.basis.kotlin.ui.css

/**
 * The `white-space` property specifies how white-space inside an element is handled.
 *
 * @author Marcel Ziganow (zim)
 */
enum class CSSWhiteSpace(internal val value: String) {

  /**
   * 	Sequences of whitespace will collapse into a single whitespace. Text will wrap when necessary. This is default
   */
  NORMAL("normal"),

  /**
   * 	Sequences of whitespace will collapse into a single whitespace. Text will never wrap to the next line. The text continues on the same line until a <br> tag is encountered
   */
  NOWRAP("nowrap"),

  /**
   * Whitespace is preserved by the browser. Text will only wrap on line breaks. Acts like the <pre> tag in HTML
   */
  PRE("pre"),

  /**
   * Sequences of whitespace will collapse into a single whitespace. Text will wrap when necessary, and on line breaks
   */
  PRE_LINE("pre-line"),

  /**
   * Whitespace is preserved by the browser. Text will wrap when necessary, and on line breaks
   */
  PRE_WRAP("pre-wrap")
}