package de.shd.basis.kotlin.ui.i18n

/**
 * Das zentrale Interface für alle sog. "I18n-Schlüssel", die dazu verwendet werden, um einen internationalisierten Text für bestimmte Sprachen
 * eindeutig auflösen zu können. Dafür wird ein textueller Schlüssel (bspw. `button.cancel`) definiert, dem für jede unterstützte Sprache eine
 * Übersetzung zugewiesen wird (bspw. die deutsche Übersetzung "Abbrechen"). Im Code wird dann ausschließlich mit dem textuellen Schlüssel (sprich dem
 * "I18n-Schlüssel") gearbeitet und daher an Framework-APIs übergeben, die anschließend das Auflösen eines solchen Schlüssels für die aktuelle Sprache
 * der Anwendung übernehmen.
 *
 * Damit textuelle Schlüssel besser bzw. sicherer refaktorisierbar sind, sollen sie einem Wert eines sog. "I18n-Enums" zugewiesen werden. Ein Enum
 * gilt als "I18n-Enum", wenn es dieses Interface implementiert und genau einen Wert pro "I18n-Schlüssel" zur Verfügung stellt. Alle Framework-APIs,
 * die eine Internationalisierung von Texten unterstützen, akzeptieren neben einem textuellen Schlüssel auch eine Implementierung von diesem Interface
 * und damit auch eines Werts eines "I18n-Enums".
 *
 * Der Name eines solchen "I18n-Enums" soll dem Muster "`${appName}I18nKey`" entsprechen.
 *
 * **Beispielhaftes I18n-Enum:**
 * ```
 *enum class AppI18nKey(private val value: String) : I18nKey {
 *
 *  BUTTON_CANCEL("button.cancel");
 *
 *  override fun get(): String {
 *    return value
 *  }
 *}
 * ```
 *
 * @author Florian Steitz (fst)
 * @see I18n
 */
interface I18nKey {

    /**
     * Gibt den zugehörigen, textuellen Schlüssel zurück, auf dessen Basis eine Übersetzung des zugehörigen Textes für die aktuelle Sprache der
     * Anwendung ermittelt werden soll.
     */
    fun get(): String
}