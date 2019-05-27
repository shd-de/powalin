package de.shd.basis.kotlin.ui.css

/**
 * Enthält alle gültigen Werte der Eigenschaft `display` von HTML-Elementen. Die jeweiligen Werte sind [hier](https://developer.mozilla.org/de/docs/Web/CSS/display)
 * dokumentiert.
 *
 * @author Florian Steitz (fst)
 */
enum class CSSDisplay(internal val value: String) {

    BLOCK("block"),
    INLINE("inline"),
    RUN_IN("run-in"),
    FLOW("flow"),
    FLOW_ROOT("flow-root"),
    TABLE("table"),
    FLEX("flex"),
    GRID("grid"),
    RUBY("ruby"),
    BLOCK_FLOW("block flow"),
    INLINE_TABLE_COMPOSED("inline table"),
    FLEX_RUN_IN("flex run-in"),
    LIST_ITEM("list-item"),
    LIST_ITEM_BLOCK("list-item block"),
    LIST_ITEM_INLINE("list-item inline"),
    LIST_ITEM_FLOW("list-item flow"),
    LIST_ITEM_FLOW_ROOT("list-item flow-root"),
    LIST_ITEM_BLOCK_FLOW("list-item block flow"),
    LIST_ITEM_BLOCK_FLOW_ROOT("list-item block flow-root"),
    FLOW_LIST_ITEM_BLOCK("flow list-item block"),
    TABLE_ROW_GROUP("table-row-group"),
    TABLE_HEADER_GROUP("table-header-group"),
    TABLE_FOOTER_GROUP("table-footer-group"),
    TABLE_ROW("table-row"),
    TABLE_CELL("table-cell"),
    TABLE_COLUMN_GROUP("table-column-group"),
    TABLE_COLUMN("table-column"),
    TABLE_CAPTION("table-caption"),
    RUBY_BASE("ruby-base"),
    RUBY_TEXT("ruby-text"),
    RUBY_BASE_CONTAINER("ruby-base-container"),
    RUBY_TEXT_CONTAINER("ruby-text-container"),
    CONTENTS("contents"),
    NONE("none"),
    INLINE_BLOCK("inline-block"),
    INLINE_TABLE("inline-table"),
    INLINE_FLEX("inline-flex"),
    INLINE_GRID("inline-grid"),
    INHERIT("inherit"),
    INITIAL("initial"),
    UNSET("unset")
}