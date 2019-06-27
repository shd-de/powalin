package de.shd.basis.kotlin.ui.component.layout

import de.shd.basis.kotlin.ui.component.layout.base.AbstractFlexLayout
import de.shd.basis.kotlin.ui.theme.BasisTheme
import de.shd.basis.kotlin.ui.util.function.withStyleName

/**
 * Erweitert die Standard-Komponente [HorizontalLayout] um eine Fluent-API, eine Internationalisierungs-API und zusätzliche Hilfsmethoden.
 *
 * @author Florian Steitz (fst)
 */
class SHDHorizontalLayout : AbstractFlexLayout<SHDHorizontalLayout>() {

    init {
        rootNode.withStyleName(BasisTheme.CLASS_HORIZONTAL)
    }
}
