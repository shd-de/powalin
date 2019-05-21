package de.shd.basis.kotlin.ui.i18n

/**
 * Das zentrale Interface für Provider von Übersetzungen für unterstützte Sprachen auf Basis von sog. "I18n-Schlüsseln" (siehe [I18nKey]). Ein solcher
 * Provider muss in der Lage sein, auch Platzhalter in Übersetzungen auflösen zu können. Ihm ist dabei allerdings überlassen, was für ein Format
 * solche Platzhalter haben.
 *
 * @author Florian Steitz (fst)
 * @see I18n
 */
interface I18nMessageProvider {

    /**
     * Versucht, eine passende Übersetzung für die spezifizierte Sprache und den übergebenen I18n-Schlüssel zu ermitteln. Falls keine passende
     * Übersetzung ermittelt werden konnte, wird der übergebene I18n-Schlüssel wieder zurückgegeben.
     *
     * Wenn optionale, variable Argumente an diese Methode übergeben werden und eine passende Übersetzung mit Platzhaltern ermittelt werden konnte,
     * werden diese Platzhalter durch die übergebenen Argumente ersetzt.
     */
    fun getMessage(language: String, i18nKey: String, vararg args: String?): String
}
