package de.shd.basis.kotlin.ui.rest

import kotlinx.serialization.Serializable

/**
 * Ein allgemeines Message-Objekt, das von REST-Endpunkten innerhalb von Response-Objekten zurückgegeben werden kann, um Aufrufern Warnungs- und
 * Info-Meldungen mitzuteilen.
 *
 * Der konkrete Text in `message` soll anwenderfreundlich und im Idealfall lokalisiert sein, damit eine UI diesen Text direkt anzeigen kann. Falls
 * dies nicht möglich ist, soll das Feld `messageCode` gesetzt sein, damit eine UI anhand des Codes eine passende Meldung anzeigen kann.
 *
 * @author Florian Steitz (fst)
 */
@Serializable
data class RESTResponseMessage(val message: String?, val messageCode: Int?)

/**
 * Eine Basisklasse für Datentransferobjekte, die von REST-Endpunkten innerhalb von Response-Objekten zurückgegeben werden können, die Hypermedia auf
 * Basis von Spring HATEOAS unterstützen. Diese Klasse ermöglicht das mitteilen von Links, die relevant zum zugehörigen Datentransferobjekt sind und
 * die ein Client bei Bedarf aufrufen kann.
 *
 * @author Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
open class RESTHypermediaResource(open val links: Collection<RESTLink>)

/**
 * Repräsentiert einen Link, der auf einen REST-Endpunkt zeigt. Ein solcher Link kann über diese Klasse mit zusätzlichen Metainformationen
 * angereichert sein, auf die ein Aufrufer bei Bedarf (vorab) reagieren kann.
 *
 * @author Florian Steitz (fst)
 */
@Serializable
data class RESTLink(
        val rel: String,
        val href: String,
        val hreflang: String?,
        val media: String?,
        val title: String?,
        val type: String?,
        val deprecation: String?
)