@file:Suppress("unused")

package de.shd.basis.kotlin.ui.http.auth

import de.shd.basis.kotlin.ui.util.constant.COLON
import org.w3c.dom.WindowOrWorkerGlobalScope
import kotlin.browser.window

/**
 * Eine Implementierung von [HTTPAuthorization] für die sog. Basic Authorization. Sie benötigt zwingend einen Benutzernamen und ein Passwort als
 * Zugangsdaten. Diese werden anschließend, getrennt durch einen Doppelpunkt, konkateniert und in Base64 enkodiert. D.h. das Property [encodedCredentials]
 * enthält den folgenden Wert, der lazy erzeugt wird:
 *
 * `base64(username:password)`
 *
 * @author Florian Steitz (fst)
 */
data class HTTPBasicAuthorization(private val username: String, private val password: String) : HTTPAuthorization {

    /**
     * Definiert `Basic` als Authentifizierungsmethode.
     */
    override val authenticationMethod: String
        get() = "Basic"

    /**
     * Enthält die übergebenen, in Base64 enkodierten Zugangsdaten. Der Wert wird wie folgt lazy erzeugt:
     *
     * `base64(username:password)`
     *
     * @see WindowOrWorkerGlobalScope.btoa
     */
    override val encodedCredentials: String
        get() = window.btoa(username + COLON + password)
}