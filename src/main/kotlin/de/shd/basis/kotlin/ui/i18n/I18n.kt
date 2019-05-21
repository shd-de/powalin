package de.shd.basis.kotlin.ui.i18n

import de.shd.basis.kotlin.ui.app.SHDApp
import de.shd.basis.kotlin.ui.mvc.controller.MVCController
import de.shd.basis.kotlin.ui.mvc.view.MVCView
import org.w3c.dom.Navigator
import kotlin.browser.window

/**
 * Das zentrale Singleton-Objekt des Frameworks, das die gesamte Logik kapselt, auf der Framework-APIs für die Internationalisierung und Lokalisierung
 * basieren. Anwendungen können dabei bei Bedarf direkt auf die API dieses Objekts zugreifen. Allerdings stellt das Framework eine Reihe von
 * "Convenience-APIs" zur Verfügung (z.B. durch die abstrakten Standard-Implementierungen von [MVCView] und [MVCController]), die stattdessen
 * verwendet werden sollen.
 *
 * @author Florian Steitz (fst)
 * @see I18nMessageProvider
 * @see I18nKey
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
object I18n {

    /**
     * Legt die aktuelle Sprache der Anwendung fest. Standardmäßig enthält dieses Property den Sprach-Code der Sprache, der von der UI des Webbrowser
     * verwendet wird. Falls die Anwendung eine andere Sprache verwenden soll (z.B. eine fixe Sprache oder die, die im HTTP-Header `Accept-Language`
     * angegeben ist), muss der Wert dieses Propertys explizit mit dem Sprach-Code der Sprache überschrieben werden, die die Anwendung verwenden soll.
     *
     * Das Format, das Sprach-Codes haben müssen, ist durch [BCP 47](https://tools.ietf.org/rfc/bcp/bcp47.txt) festgelegt. Zum Beispiel sind die
     * folgenden Sprach-Code-Formate valide:
     * - en
     * - en-US
     * - de
     * - de-DE
     *
     * Sobald der Wert dieses Propertys mit einem validen Sprach-Code überschrieben wird, verwenden alle folgenden Aufrufe von Sprach-spezifischen
     * APIs des Frameworks anschließend die neue Sprache. Dies betrifft auch die API jeder Komponente des Frameworks und der Anwendung.
     *
     * Falls die Anwendung bereits internationalisierte Texte anzeigt, müssen diese nach Aufruf dieser Methode manuell aktualisiert werden, da das
     * Framework dies nicht automatisch tun kann.
     *
     * @see Navigator.language
     */
    var currentLanguage = window.navigator.language

    /**
     * Legt die Implementierung von [I18nMessageProvider] fest, die zum Auflösen von Übersetzung auf Basis von I18n-Schlüsseln verwendet wird. Der
     * Wert dieses Propertys soll nur über ausgewählte, öffentliche APIs geändert werden können. D.h. im Regelfall nur über die API von [SHDApp].
     *
     * Derzeit wird der [DummyI18nMessageProvider] als Standard-Implementierung von [I18nMessageProvider] verwendet, der den übergebenen
     * I18n-Schlüssel direkt wieder unverändert zurückgibt. D.h. damit eine Anwendung internationalisiert werden kann, muss sie einen eigenen
     * [I18nMessageProvider] über die API von [SHDApp] registrieren.
     */
    internal var messageProvider: I18nMessageProvider = DummyI18nMessageProvider()

    /**
     * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung über die zugrunde liegende
     * Implementierung von [I18nMessageProvider] zu ermitteln. Falls keine passende Übersetzung ermittelt werden konnte, wird der übergebene
     * I18n-Schlüssel wieder zurückgegeben.
     *
     * Wenn optionale, variable Argumente an diese Methode übergeben werden und eine passende Übersetzung mit Platzhaltern ermittelt werden konnte,
     * werden diese Platzhalter durch die übergebenen Argumente ersetzt.
     *
     * **Wichtig:**
     *
     * Standardmäßig wird nur eine Dummy-Implementierung von [I18nMessageProvider] verwendet, die den übergebenen I18n-Schlüssel direkt wieder
     * unverändert zurückgibt. D.h. damit diese Methode sinnvoll genutzt werden kann, muss zuvor eine eigene Implementierung von [I18nMessageProvider]
     * via [SHDApp.withI18nMessageProvider] registriert werden.
     *
     * @see [currentLanguage]
     */
    fun getText(key: I18nKey, vararg args: String?): String {
        return getText(key.get(), *args)
    }

    /**
     * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung über die zugrunde liegende
     * Implementierung von [I18nMessageProvider] zu ermitteln. Falls keine passende Übersetzung ermittelt werden konnte, wird der übergebene
     * I18n-Schlüssel wieder zurückgegeben.
     *
     * Wenn optionale, variable Argumente an diese Methode übergeben werden und eine passende Übersetzung mit Platzhaltern ermittelt werden konnte,
     * werden diese Platzhalter durch die übergebenen Argumente ersetzt.
     *
     * **Wichtig:**
     *
     * Standardmäßig wird nur eine Dummy-Implementierung von [I18nMessageProvider] verwendet, die den übergebenen I18n-Schlüssel direkt wieder
     * unverändert zurückgibt. D.h. damit diese Methode sinnvoll genutzt werden kann, muss zuvor eine eigene Implementierung von [I18nMessageProvider]
     * via [SHDApp.withI18nMessageProvider] registriert werden.
     *
     * @see [currentLanguage]
     */
    fun getText(key: String, vararg args: String?): String {
        return messageProvider.getMessage(currentLanguage, key, *args)
    }

    /**
     * Eine Dummy-Implementierung von [I18nMessageProvider], die den übergebenen I18n-Schlüssel direkt wieder unverändert zurückgibt.
     */
    private class DummyI18nMessageProvider : I18nMessageProvider {

        /**
         * Gibt den übergebenen I18n-Schlüssel direkt wieder unverändert zurück.
         */
        override fun getMessage(language: String, i18nKey: String, vararg args: String?): String {
            return i18nKey
        }
    }
}