@file:Suppress("unused")

package de.shd.basis.kotlin.ui.http.auth

import de.shd.basis.kotlin.ui.util.constant.COLON
import kotlin.browser.window

/**
 *
 * @author Florian Steitz (fst)
 */
data class HTTPBasicAuthorization(private val username: String, private val password: String) : HTTPAuthorization {

    override val authenticationMethod: String
        get() = "Basic"

    override val encodedCredentials: String
        get() = window.btoa(username + COLON + password)
}