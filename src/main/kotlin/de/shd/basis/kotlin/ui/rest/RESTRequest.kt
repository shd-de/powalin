package de.shd.basis.kotlin.ui.rest

import kotlinx.serialization.Serializable

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
 * Standard-Request zum Erstellen und Aktualisieren eines Elements. Er ist das Gegenstück zum gleichnamigen, serverseitigen Request.
 *
 * @author Florian Steitz (fst)
 */
@Serializable
@Suppress("unused")
data class RESTSaveElementRequest<DATATYPE>(val elementToSave: DATATYPE) : AbstractRESTRequest()

/**
 *
 */
@Serializable
@Suppress("unused")
data class RESTReadElementByIdRequest<DATATYPE>(val id: DATATYPE) : AbstractRESTRequest()