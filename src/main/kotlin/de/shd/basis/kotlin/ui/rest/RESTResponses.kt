package de.shd.basis.kotlin.ui.rest

import kotlinx.serialization.SerialName
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
        open val errorNumber: String? = null,
        open val errorMessage: String? = null,
        open val warningList: Collection<RESTResponseMessage>,
        open val infoList: Collection<RESTResponseMessage>,
        open val content: CONTENT
)

/**
 * Die Standard-Response, die von REST-Endpunkten zurückgegeben wird, wenn genau ein Element ausgelesen werden soll. Sie ist das Gegenstück zur
 * gleichnamigen, serverseitigen Response.
 *
 * @author Tobias Isekeit (ist), Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
open class RESTReadElementResponse<CONTENT>(
        open val status: String,
        open val errorNumber: String? = null,
        open val errorMessage: String? = null,
        open val warningList: Collection<RESTResponseMessage>,
        open val infoList: Collection<RESTResponseMessage>,
        @SerialName("value") val content: CONTENT
) // : RESTResponse<CONTENT>(status, errorNumber, errorMessage, warningList, infoList, content) TODO Verursacht NPE in kotlinx.serialization

/**
 * Die Standard-Response, die von REST-Endpunkten zurückgegeben wird, wenn genau ein Element erstellt bzw. aktualisiert werden soll. Sie ist das
 * Gegenstück zur gleichnamigen, serverseitigen Response.
 *
 * @author Tobias Isekeit (ist), Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
open class RESTSavedElementResponse<CONTENT>(
        open val status: String,
        open val errorNumber: String? = null,
        open val errorMessage: String? = null,
        open val warningList: Collection<RESTResponseMessage>,
        open val infoList: Collection<RESTResponseMessage>,
        @SerialName("value") val content: CONTENT?
) // : RESTResponse<CONTENT>(status, errorNumber, errorMessage, warningList, infoList, content) TODO Verursacht NPE in kotlinx.serialization
