package de.shd.basis.kotlin.ui.css

/**
 * Enthält die wichtigsten Keywords, die als Wert von bestimmten Eigenschaften von HTML-Elementen gesetzt werden können.
 *
 * @author Marcel Ziganow (zim)
 */
enum class CSSKeyword(internal val value: String) {

  /**
   * Keyword für Breiten- und Höhenangaben. Der Browser berechnet hierdurch den konkreten Wert selbst.
   */
  AUTO("auto"),

  /**
   * Der inherit CSS Wert lässt das Element, für das es angegeben wurde, den berechneten Wert der Eigenschaft seines
   * Elternelements annehmen.
   *
   * Siehe [MDN web docs: Inherit](https://developer.mozilla.org/en-US/docs/Web/CSS/inherit)
   */
  INHERIT("inherit"),

  /**
   * Dieses Schlüsselwort setzt die Eigenschaft auf ihren geerbten Wert zurück, falls er vom Elternelement
   * geerbt wurde, oder andernfalls auf den ursprünglichen Wert.
   *
   * Siehe [MDN web docs: Inherit](https://developer.mozilla.org/en-US/docs/Web/CSS/unset)
   */
  UNSET("unset"),

  /**
   * Der Wert initial repräsentiert einen vom Browser vorgegebenen Standardwert.
   *
   * Siehe [MDN web docs: Inherit](https://developer.mozilla.org/en-US/docs/Web/CSS/initial)
   */
  INITIAL("initial")
}