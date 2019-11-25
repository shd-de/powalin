package de.shd.basis.kotlin.ui.component

import de.shd.basis.kotlin.ui.component.window.SHDWindow
import de.shd.basis.kotlin.ui.i18n.I18n
import de.shd.basis.kotlin.ui.i18n.I18nKey
import de.shd.basis.kotlin.ui.util.function.withClickListener
import de.shd.basis.kotlin.ui.window.WindowManager
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import kotlin.browser.document

/**
 * Die abstrakte Standard-Implementierung von [SHDUIComponent], von der jede UI-Komponente erben soll.
 *
 * Diese Klasse implementiert zusätzlich noch standardmäßig das Interface [SHDFluentUIComponent], das die Extension-Functions mit dem Präfix `with`
 * des [Wurzelknotens][HTMLElement] dieser Komponente zur öffentlichen API der Komponente selbst hinzufügt.  Daher dient der zu spezifizierende,
 * generische Parameter nur dazu, Fluent-APIs zu ermöglichen. D.h. Ableitungen von dieser Klasse müssen sich selbst als Parameter angeben.
 *
 * @author Florian Steitz (fst)
 */
abstract class AbstractUIComponent<COMPONENT : SHDFluentUIComponent<COMPONENT>> : SHDFluentUIComponent<COMPONENT> {

    // Ableitende Komponenten sollen mit diesem Alias arbeiten, damit das Framework bei Bedarf eine eigene Factory zum Erzeugen von HTML-Elementen zur
    // Verfügung stellen kann, ohne dabei Migrationsaufwand zu verursachen.
    protected val nodeFactory = document.create

    override fun addClickListener(clickListener: () -> Unit) {
        rootNode.withClickListener(clickListener)
    }

    override fun addClickListener(clickListener: (Event) -> Unit) {
        rootNode.withClickListener(clickListener)
    }

    /**
     * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung über [I18n.getText] zu
     * ermitteln. Falls keine passende Übersetzung ermittelt werden konnte, wird der übergebene I18n-Schlüssel wieder zurückgegeben.
     *
     * Wenn optionale, variable Argumente an diese Methode übergeben werden und eine passende Übersetzung mit Platzhaltern ermittelt werden konnte,
     * werden diese Platzhalter durch die übergebenen Argumente ersetzt.
     *
     * @see [I18n.currentLanguage]
     */
    protected fun getText(key: I18nKey, vararg args: String?): String {
        return I18n.getText(key, *args)
    }

    /**
     * Versucht, anhand des übergebenen I18n-Schlüssels eine passende Übersetzung für die aktuelle Sprache der Anwendung über [I18n.getText] zu
     * ermitteln. Falls keine passende Übersetzung ermittelt werden konnte, wird der übergebene I18n-Schlüssel wieder zurückgegeben.
     *
     * Wenn optionale, variable Argumente an diese Methode übergeben werden und eine passende Übersetzung mit Platzhaltern ermittelt werden konnte,
     * werden diese Platzhalter durch die übergebenen Argumente ersetzt.
     *
     * @see [I18n.currentLanguage]
     */
    protected fun getText(key: String, vararg args: String?): String {
        return I18n.getText(key, *args)
    }

    protected fun showWindow(window: SHDWindow) {
        WindowManager.showWindow(window)
    }
}
