package de.shd.basis.kotlin.ui.http.auth

import de.shd.basis.kotlin.ui.util.constant.SPACE

/**
 *
 * @author Florian Steitz (fst)
 */
interface HTTPAuthorization {

    /**
     *
     */
    val authenticationMethod: String

    /**
     *
     */
    val encodedCredentials: String

    /**
     *
     */
    fun toAuthorizationHeaderValue(): String {
        return authenticationMethod + SPACE + encodedCredentials
    }
}