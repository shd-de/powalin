package de.shd.basis.kotlin.ui.i18n

import kotlinx.html.INPUT
import kotlinx.html.Tag
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.Node

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Kind-Knoten zu diesem Element hinzuzufügen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Kind-Knoten zu diesem Element
 * hinzuzufügt.
 *
 * @see I18n.currentLanguage
 * @see Tag.text
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
 * @see Tag.text
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
 * @see Node.textContent
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun Node.withI18nTextContent(i18nKey: I18nKey, vararg args: String?): Node {
    return withI18nTextContent(i18nKey.get(), *args)
}

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Kind-Knoten zu diesem Element hinzuzufügen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Kind-Knoten zu diesem Element
 * hinzuzufügt.
 *
 * @see I18n.currentLanguage
 * @see Node.textContent
 * @author Florian Steitz (fst)
 */
fun Node.withI18nTextContent(i18nKey: String, vararg args: String?): Node {
    textContent = I18n.getText(i18nKey, *args)
    return this
}

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Platzhalterwert dieses Eingabeelements zu setzen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Platzhalterwert dieses Eingabeelements
 * gesetzt.
 *
 * @see I18n.currentLanguage
 * @see INPUT.placeholder
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun INPUT.i18nPlaceholder(i18nKey: I18nKey, vararg args: String?) {
    i18nPlaceholder(i18nKey.get(), *args)
}

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Platzhalterwert dieses Eingabeelements zu setzen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Platzhalterwert dieses Eingabeelements
 * gesetzt.
 *
 * @see I18n.currentLanguage
 * @see INPUT.placeholder
 * @author Florian Steitz (fst)
 */
fun INPUT.i18nPlaceholder(i18nKey: String, vararg args: String?) {
    placeholder = I18n.getText(i18nKey, *args)
}

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Platzhalterwert dieses Eingabeelements zu setzen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Platzhalterwert dieses Eingabeelements
 * gesetzt.
 *
 * @see I18n.currentLanguage
 * @see HTMLInputElement.placeholder
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
fun HTMLInputElement.withI18nPlaceholder(i18nKey: I18nKey, vararg args: String?): HTMLInputElement {
    return withI18nPlaceholder(i18nKey.get(), *args)
}

/**
 * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung via [I18n.getText] zu ermitteln
 * und diese Übersetzung als Platzhalterwert dieses Eingabeelements zu setzen.
 *
 * Falls keine passende Übersetzung ermittelt werden konnte, wird stattdessen der übergebene I18n-Schlüssel als Platzhalterwert dieses Eingabeelements
 * gesetzt.
 *
 * @see I18n.currentLanguage
 * @see HTMLInputElement.placeholder
 * @author Florian Steitz (fst)
 */
fun HTMLInputElement.withI18nPlaceholder(i18nKey: String, vararg args: String?): HTMLInputElement {
    placeholder = I18n.getText(i18nKey, *args)
    return this
}