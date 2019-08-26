package de.shd.basis.kotlin.ui.rest

import de.shd.basis.kotlin.ui.rest.read.filter.Filter
import de.shd.basis.kotlin.ui.rest.read.limit.Range
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Allgemeines, clientseitiges Basis-Interface für REST-Request-Klassen. Es ist das Gegenstück zum gleichnamigen, serverseitigen Interface.
 *
 * @author Florian Steitz (fst)
 */
interface RESTRequest

/**
 * Allgemeine, clientseitige Basis-Klasse für REST-Request-Klassen, die das Interface [RESTRequest] implementiert. Sie ist das Gegenstück zur
 * gleichnamigen, serverseitigen Klasse.
 *
 * @author Florian Steitz (fst)
 */
@Serializable
abstract class AbstractRESTRequest : RESTRequest

/**
 * Ein abstrakter Standard-Request für REST-Request-Klassen zum Auslesen beliebig vieler Elemente. Sie ist das Gegenstück zur gleichnamigen,
 * serverseitigen Klasse.
 *
 * @author Marcel Ziganow (zim), Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
abstract class AbstractRESTReadListRequest(open var range: Range? = null) : AbstractRESTRequest()

/**
 * Standard-Request zum Erstellen und Aktualisieren eines Elements. Er ist das Gegenstück zum gleichnamigen, serverseitigen Request.
 *
 * @author Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
data class RESTSaveElementRequest<DATATYPE>(val elementToSave: DATATYPE) : AbstractRESTRequest()


/**
 * Standard-Request zum Erstellen und Aktualisieren einer Liste von Elementen. Es ist das Gegenstück zum gleichnamigen, serverseitigen Request.
 *
 * @author Marcel Ziganow (zim)
 */
@Serializable
@Suppress("unused")
data class RESTSaveListRequest<DATATYPE>(val listToSave: MutableList<DATATYPE>) : AbstractRESTRequest()

/**
 * Standard-Request zum Auslesen eines Elements anhand dessen ID. Er ist das Gegenstück zum gleichnamigen, serverseitigen Request.
 *
 * @author Tobias Isekeit (ist)
 */
@Serializable
@Suppress("unused")
data class RESTReadElementByIdRequest<DATATYPE>(val id: DATATYPE) : AbstractRESTRequest()

/**
 * Standard-Request zum Anmelden eines Benutzers
 *
 * @author Marcel Ziganow
 */
@Serializable
@Suppress("unused")
open class RESTLoginRequest(open val username: String, open val password: String) : AbstractRESTRequest()

/**
 * Standard-Request zum Auslesen einer Liste von Elementen anhand eines Filters. Er ist das Gegenstück zum gleichnamigen, serverseitigen Request.
 *
 * @author Marcel Ziganow (zim), Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
data class RESTReadListByFilterRequest<FILTER : Filter>(
        val filter: FILTER,
        @Transient override var range: Range? = null
) : AbstractRESTReadListRequest(range)
