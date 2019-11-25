package de.shd.basis.kotlin.ui.rest.read.filter

/**
 * @see de.shd.basis.api.read.filter.FilterOperation
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
enum class FilterOperation {
  EQUAL_TO,
  STARTS_WITH,
  ENDS_WITH,
  CONTAINS,
  IS_MEMBER,
  LESS_THAN,
  LESS_EQUAL_THAN,
  GREATER_THAN,
  GREATER_EQUAL_THAN

}