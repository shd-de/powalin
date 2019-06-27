package de.shd.basis.kotlin.ui.component

import de.shd.basis.kotlin.ui.util.function.withClickListener
import kotlinx.html.dom.create
import kotlin.browser.document

/**
 * Die abstrakte Standard-Implementierung von [SHDUIComponent], von der jede UI-Komponente erben soll.
 *
 * @author Florian Steitz (fst)
 */
abstract class AbstractUIComponent : SHDUIComponent {

    // Ableitende Komponenten sollen mit diesem Alias arbeiten, damit das Framework bei Bedarf eine eigene Factory zum Erzeugen von HTML-Elementen zur
    // Verfügung stellen kann, ohne dabei Migrationsaufwand zu verursachen.
    protected val nodeFactory = document.create

    override fun addClickListener(clickListener: () -> Unit) {
        rootNode.withClickListener(clickListener)
    }
}
