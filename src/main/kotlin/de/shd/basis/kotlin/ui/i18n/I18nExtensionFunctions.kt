package de.shd.basis.kotlin.ui.i18n

import kotlinx.html.Tag
import org.w3c.dom.Node

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Kind-Knoten zu diesem Element hinzuzufügen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Kind-Knoten zu diesem Element
 * hinzuzufügt.
 *
 * @see I18n.currentLanguage
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun Tag.i18nText(i18nKey: I18nKey, vararg args: String?) {
    i18nText(i18nKey.get(), *args)
}

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Kind-Knoten zu diesem Element hinzuzufügen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Kind-Knoten zu diesem Element
 * hinzuzufügt.
 *
 * @see I18n.currentLanguage
 * @author Florian Steitz (fst)
 */
fun Tag.i18nText(i18nKey: String, vararg args: String?) {
    text(I18n.getText(i18nKey, *args))
}

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Kind-Knoten zu diesem Element hinzuzufügen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Kind-Knoten zu diesem Element
 * hinzuzufügt.
 *
 * @see I18n.currentLanguage
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun Node.setI18nTextContent(i18nKey: I18nKey, vararg args: String?) {
    setI18nTextContent(i18nKey.get(), *args)
}

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Kind-Knoten zu diesem Element hinzuzufügen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Kind-Knoten zu diesem Element
 * hinzuzufügt.
 *
 * @see I18n.currentLanguage
 * @author Florian Steitz (fst)
 */
fun Node.setI18nTextContent(i18nKey: String, vararg args: String?) {
    textContent = I18n.getText(i18nKey, *args)
}