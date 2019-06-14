package de.shd.basis.kotlin.ui.rest

import kotlinx.serialization.Serializable

/**
 * Die Basisklasse aller Response-Objekte, die von REST-Endpunkten zurückgegeben werden.
 *
 * @author Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
abstract class AbstractRESTResponse(
        open val status: String,
        open val errorNumber: String? = null,
        open val errorMessage: String? = null,
        open val warningList: Collection<RESTResponseMessage>,
        open val infoList: Collection<RESTResponseMessage>
)
