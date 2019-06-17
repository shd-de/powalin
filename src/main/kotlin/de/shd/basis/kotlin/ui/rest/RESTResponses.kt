package de.shd.basis.kotlin.ui.rest

import kotlinx.serialization.Serializable

/**
 * Die Standard-Response, die von REST-Endpunkten zurückgegeben wird.
 *
 * @author Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
open class RESTResponse<CONTENT>(
        open val status: String,
        open val errorNumber: String?,
        open val errorMessage: String?,
        open val warningList: Collection<RESTResponseMessage>,
        open val infoList: Collection<RESTResponseMessage>,
        open val content: CONTENT
)
