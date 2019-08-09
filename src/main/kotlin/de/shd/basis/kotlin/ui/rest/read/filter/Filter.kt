package de.shd.basis.kotlin.ui.rest.read.filter

/**
 * @see de.shd.basis.api.read.filter.Filter
 * @author Marcel Ziganow (zim)
 */
@Suppress("unused")
interface Filter {

    /**
     * @return true wenn dieser Filter ein Invertierter Filter ist.
     */
    val isInverted: Boolean
}
