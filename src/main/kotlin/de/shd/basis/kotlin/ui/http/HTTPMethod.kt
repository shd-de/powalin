package de.shd.basis.kotlin.ui.http

/**
 * Enthält alle gültigen "HTTP-Methoden", die u.a. [hier](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Request_methods) dokumentiert sind.
 *
 * HTTP-Methoden fungieren oftmals als eine Art Typisierung von Operationen, die durch HTTP-Requests ausgelöst werden sollen und vermitteln
 * dadurch auch oftmals die Intention des Clients. Zum Beispiel wird [GET] meist für lesende Anfragen verwendet. [POST], [PUT], [PATCH] und [DELETE]
 * werden hingegen meist für schreibende Anfragen verwendet.
 *
 * @author Florian Steitz (fst)
 */
enum class HTTPMethod {

    GET,
    HEAD,
    POST,
    PUT,
    DELETE,
    CONNECT,
    OPTIONS,
    TRACE,
    PATCH
}