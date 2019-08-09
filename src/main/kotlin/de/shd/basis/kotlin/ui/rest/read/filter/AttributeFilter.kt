package de.shd.basis.kotlin.ui.rest.read.filter

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable

/**
 * Vereinfachte Form des AttributeFilter
 * @see de.shd.basis.api.read.filter.AttributeFilter
 * @author Marcel Ziganow (zim)
 */
@Serializable
class AttributeFilter(override var isInverted: Boolean = false, var operation: FilterOperation = FilterOperation.EQUAL_TO, var isCaseSensitive: Boolean = false) : Filter {

  /**
   * Der Name des Feldes, auf das sich die Filterbedingung bezieht.
   */
  private var fieldName: String? = null

  /**
   * Das Element mit dem die Feldinhalte des per fieldName benannten Feldes verglichen werden
   */
  @ContextualSerialization
  private var referenceValue: Any? = null

  /**
   * Erstellt eine AttributeFilter die das benannte Feld fieldName auf Gleichheit zu dem uebergebenen Referenzwert
   * ueberprueft.
   *
   * @param fieldName      der Feldname der fuer den Vergleich anhand dieser Bedingung verwendet wird
   * @param referenceValue der Referenzwert mit dem die Werte des Feldes mit dem gegebenen Namen verglichen werden
   */
  constructor(fieldName: String, referenceValue: Any) : this() {
    this.fieldName = fieldName
    this.referenceValue = referenceValue
  }


  /**
   * Erstellt eine AttributeFilter die das benannte Feld fieldName anhand der uebergebenen Operation mit dem
   * uebergebenen Referenzwert vergleicht.
   *
   * @param fieldName      der Feldname der fuer den Vergleich anhand dieser Bedingung verwendet wird
   * @param referenceValue der Referenzwert mit dem die Werte des Feldes mit dem gegebenen Namen verglichen werden
   * @param operation      die zum Vergleich zu verwendende Operation
   */
  constructor(fieldName: String, referenceValue: Any, operation: FilterOperation) : this(fieldName, referenceValue) {
    this.operation = operation
  }


  /**
   * Erstellt eine AttributeFilter die das benannte Feld fieldName anhand der uebergebenen Operation und der Festlegung
   * fuer CaseSensitivity mit dem uebergebenen Referenzwert vergleicht.
   *
   * @param fieldName      der Feldname der fuer den Vergleich anhand dieser Bedingung verwendet wird
   * @param referenceValue der Referenzwert mit dem die Werte des Feldes mit dem gegebenen Namen verglichen werden
   * @param operation      die zum Vergleich zu verwendende Operation
   * @param caseSensitive  gibt an, ob der Vergleich Gross/Kleinschreibung beruecksichtigen soll
   */
  constructor(fieldName: String, referenceValue: Any, operation: FilterOperation, caseSensitive: Boolean) : this(fieldName, referenceValue, operation) {
    this.isCaseSensitive = caseSensitive
  }
}
