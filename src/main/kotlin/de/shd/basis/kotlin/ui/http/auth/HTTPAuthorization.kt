package de.shd.basis.kotlin.ui.http.auth

import de.shd.basis.kotlin.ui.http.HTTPRequest
import de.shd.basis.kotlin.ui.util.constant.SPACE

/**
 * Das zentrale Interface für Klassen, die entscheiden, wie ein Wert des HTTP-Headers `Authorization` bspw. auf Basis von Zugangsdaten (oder eines
 * Tokens) erzeugt werden muss, damit ein [HTTPRequest] von einem Backend (erfolgreich) authentifiziert und authorisiert werden kann.
 *
 * Im Regelfall ist ein Wert des HTTP-Headers `Authorization` zusammengesetzt aus der Authentifizierungsmethode (z.B. `Basic`), einem Leerzeichen und
 * den eigentlichen Zugangsdaten (oder dem Token). Je nach Authentifizierungsmethode werden die Zugangsdaten entweder in Klartext mitgesendet (bspw.
 * wenn es sich dabei um einen Token handelt) oder vor dem Senden enkodiert (z.B. in Base64).
 *
 * @author Florian Steitz (fst)
 */
interface HTTPAuthorization {

    /**
     * Enthält die Authentifizierungsmethode, die die Implementierung dieses Interfaces unterstützt. Der enthaltene Wert wird als ein Bestandteil
     * eines Werts des HTTP-Headers `Authorization` mitgesendet.
     */
    val authenticationMethod: String

    /**
     * Enthält die (ggf. enkodierten) Zugangsdaten, die als ein Bestandteil eines Werts des HTTP-Headers `Authorization` mitgesendet werden.
     */
    val encodedCredentials: String

    /**
     * Erzeugt auf Basis der enthaltenen Informationen einen Wert für den HTTP-Header `Authorization`. Der hierüber erzeugte Wert hat das folgende
     * Format:
     *
     * "`$AUTHENTICATION_METHOD $CREDENTIALS`"
     */
    fun toAuthorizationHeaderValue(): String {
        return authenticationMethod + SPACE + encodedCredentials
    }
}