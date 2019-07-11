package de.shd.basis.kotlin.ui.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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

@Serializable
@Suppress("unused")
open class RESTReadElementResponse<CONTENT>(
        val status: String,
        val errorNumber: String?,
        val errorMessage: String?,
        val warningList: Collection<RESTResponseMessage>,
        val infoList: Collection<RESTResponseMessage>,
        @SerialName("value") val content: CONTENT
) // : RESTResponse<CONTENT>(status, errorNumber, errorMessage, warningList, infoList, content)

@Serializable
@Suppress("unused")
open class RESTSavedElementResponse<CONTENT>(
        val status: String,
        val errorNumber: String?,
        val errorMessage: String?,
        val warningList: Collection<RESTResponseMessage>,
        val infoList: Collection<RESTResponseMessage>,
        @SerialName("value") val content: CONTENT
) // : RESTResponse<CONTENT>(status, errorNumber, errorMessage, warningList, infoList, content)
