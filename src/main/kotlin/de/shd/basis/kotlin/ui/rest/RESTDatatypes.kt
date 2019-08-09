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

/**
 * Eine einfache Implementierung von [Collection] für eine (ggf. eingeschränkte) Liste von Datentransferobjekten, die von REST-Endpunkten innerhalb
 * von Response-Objekten zurückgegeben werden kann.
 *
 * Eingeschränkt bedeutet, dass diese Liste, abhängig vom Request, ggf. nur eine Untermenge der Elemente enthält, die ein Endpunkt bzw. Service
 * insgesamt verwaltet. Bspw. können Requests so strukturiert werden, dass ein Endpunkt maximal 50 Elemente pro Request zurückgibt. Wodurch ein
 * (performanterer) Paging-Mechanismus implementiert werden kann.
 *
 * Ob diese Liste vollständig ist oder nicht, kann anhand der Felder `totalNumberOfElements`, `from` und `to` geprüft werden. Das Feld
 * `totalNumberOfElements` gibt stets an, wie viele Elemente ein Endpunkt bzw. Service insgesamt vom jeweiligen fachlichen Datentyp verwaltet.
 *
 * Diese Liste fungiert dabei als clientseitiges Pendant zu `HypermediaResourceList`, um entsprechende Responses deserialisieren zu können. Daher
 * erlaubt diese Liste auch nur lesende Operationen.
 *
 * Die Klasse `HypermediaResourceList` ist Teil des Microservice-Stacks und ihre API orientiert sich an `RESTReadListResponse`.
 *
 * @author Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
class RESTList<ITEM>(val totalNumberOfElements: Int, val from: Int, val to: Int, private val value: Collection<ITEM>) : Collection<ITEM> {
    override val size: Int
        get() = value.size

    override fun contains(element: ITEM): Boolean {
        return value.contains(element)
    }

    override fun containsAll(elements: Collection<ITEM>): Boolean {
        return this.value.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return value.isEmpty()
    }

    override fun iterator(): Iterator<ITEM> {
        return value.iterator()
    }
}