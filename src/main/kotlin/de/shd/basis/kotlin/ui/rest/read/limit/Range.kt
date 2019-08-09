package de.shd.basis.kotlin.ui.rest.read.limit

import kotlinx.serialization.Serializable

/**
 * Definition eines Bereiches von Elementen einer Liste, entspricht in etwa einer limit-Clause in SQL.
 * Das erste Element hat den Index 0.
 *
 * @see de.shd.basis.api.read.limit.Range
 * @author Marcel Ziganow (zim)
 */
@Serializable
class Range(
    var from: Int = 0,
    var to: Int = -1
)
