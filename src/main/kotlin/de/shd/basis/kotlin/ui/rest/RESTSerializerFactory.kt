package de.shd.basis.kotlin.ui.rest

import kotlinx.serialization.KSerializer

/**
 * Eine Factory, die das Erzeugen von Implementierungen von [KSerializer] für Responses von REST-Endpunkten vereinheitlicht und vereinfacht.
 *
 * @author Florian Steitz (fst)
 */
@Suppress("unused")
object RESTSerializerFactory {

    /**
     * Erzeugt eine Implementierung von [KSerializer] für eine [RESTResponse], die nur ein einziges fachliches Datentransferobjekt enthält.
     */
    fun <ELEMENT> createForElementResponse(elementSerializer: KSerializer<ELEMENT>): KSerializer<RESTResponse<ELEMENT>> {
        return RESTResponse.serializer(elementSerializer)
    }

    /**
     * Erzeugt eine Implementierung von [KSerializer] für eine [RESTReadElementResponse], die nur ein einziges fachliches Datentransferobjekt enthält.
     */
    fun <ELEMENT> createForReadElementResponse(elementSerializer: KSerializer<ELEMENT>): KSerializer<RESTReadElementResponse<ELEMENT>> {
        return RESTReadElementResponse.serializer(elementSerializer)
    }

    /**
     * Erzeugt eine Implementierung von [KSerializer] für eine [RESTSavedElementResponse], die nur ein einziges fachliches Datentransferobjekt enthält.
     */
    fun <ELEMENT> createForSavedElementResponse(elementSerializer: KSerializer<ELEMENT>): KSerializer<RESTSavedElementResponse<ELEMENT>> {
        return RESTSavedElementResponse.serializer(elementSerializer)
    }

    /**
     * Erzeugt eine Implementierung von [KSerializer] für eine [RESTResponse], die beliebig viele fachliche Datentransferobjekte enthalten kann.
     * Solche Objekte werden dafür zu einer [RESTList] hinzugefügt, die widerum in der [RESTResponse] enthalten ist.
     */
    fun <ITEM> createForListResponse(itemSerializer: KSerializer<ITEM>): KSerializer<RESTResponse<RESTList<ITEM>>> {
        return RESTResponse.serializer(RESTList.serializer(itemSerializer))
    }
}