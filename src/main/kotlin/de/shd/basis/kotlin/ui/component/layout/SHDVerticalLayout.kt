package de.shd.basis.kotlin.ui.component.layout

import de.shd.basis.kotlin.ui.component.layout.base.AbstractFlexLayout
import de.shd.basis.kotlin.ui.theme.BasisTheme
import de.shd.basis.kotlin.ui.util.function.withStyleName

/**
 * Eine Standard-Layout-Komponente, die ihre Kindelemente bzw. ihre Kindkomponenten vertikal anordnet und u.a. Standard-Abstände zwischen ihnen
 * anzeigen kann.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
class SHDVerticalLayout : AbstractFlexLayout<SHDVerticalLayout>() {

    /**
     * Fügt eine zusätzliche CSS-Klasse zum Wurzelknoten hinzu, die dafür sorgt, dass die Kindelemente bzw. die Kindkomponenten dieser
     * Layout-Komponente vertikal angeordnet werden.
     */
    init {
        rootNode.withStyleName(BasisTheme.CLASS_VERTICAL)
    }
}
