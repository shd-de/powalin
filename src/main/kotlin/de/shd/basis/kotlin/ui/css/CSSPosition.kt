package de.shd.basis.kotlin.ui.css

/**
 * The `white-space` property specifies how white-space inside an element is handled.
 *
 * @author Marcel Ziganow (zim)
 */
enum class CSSPosition(internal val value: String) {

  /**
   * Das Element wird aus dem normalen Fluss gelöst und unabhängig verschoben. Dabei können andere Elemente verdeckt
   * werden. Diese verhalten sich so, als ob das Element nicht vorhanden wäre und lassen keinen Platz für das Element.
   */
  ABSOLUTE("absolute"),

  /**
   * Standardwert. Das Element ist im normalen Fluss.
   * Die Eigenschaften `top`, `right`, `bottom` oder `left` haben keine Auswirkungen.
   */
  STATIC("static"),

  /**
   * Das Element wird vom normalen Fluss aus verschoben und hat keinen Einfluss auf andere Elemente, da an der
   * ursprünglichen Position Platz gelassen wird. Bei ``table-*-group`, `table-row`, `table-column`, `table-cell`, und `table-caption` Elementen ist kein Effekt definiert.
   */
  RELATIVE("relative"),

  /**
   * Eine Mischung zwischen `fixed` und `relative`: Das Element wird vom normalen Fluss aus verschoben und hat keinen
   * Einfluss auf andere Elemente, solange es sich innerhalb des Viewports befindet. Sobald es sich ausserhalb befindet,
   * wird das Element aus dem normalen Fluss gelöst und bleibt so beim Scrollen an seiner fest definierten Position.
   */
  STICKY("sticky"),

  /**
   * Die Verschiebung orientiert sich am Viewport.
   * Das Element wird aus dem normalen Fluss gelöst und bleibt auch beim Scrollen an seiner fest definierten Position.
   * Beim Drucken wird das Element auf jeder Seite an der positionierten Stelle angezeigt.
   */
  FIXED("fixed")
}