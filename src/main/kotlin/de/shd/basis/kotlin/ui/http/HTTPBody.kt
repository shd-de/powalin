package de.shd.basis.kotlin.ui.http

import de.shd.basis.kotlin.ui.serialization.generator.SHDGenerator
import kotlinx.serialization.KSerializer

/**
 * Repräsentiert einen serialisierbaren Body eines HTTP-Requests. D.h. Instanzen von dieser Klasse enthalten ein Objekt, das serialisiert im Body
 * eines  HTTP-Requests mitgesendet wird und einen [Serializer][KSerializer], der in der Lage ist, dieses Objekt dafür in ein textuelles Datenformat
 * (bspw. JSON) umzuwandeln.
 *
 * In was für ein textuelles Datenformat das enthaltene Objekt umgewandelt wird, entscheidet dabei der Aufrufer der Methode [stringify], indem er eine
 * Implementierung des Interfaces [SHDGenerator] an sie übergibt, die die Generierung des Formats übernimmt. Im Regelfall ruft der [HTTPClient] diese
 * Methode auf.
 *
 * @author Florian Steitz (fst)
 */
internal class HTTPBody<DATA> internal constructor(private val data: DATA, private val serializer: KSerializer<DATA>) {

    /**
     * Serialisiert diesen Body zu einem textuellen Datenformat, das der übergebene [SHDGenerator] festlegt und dessen Generierung er übernimmt. Dafür
     * wird das enthaltene, zu serialisierende Objekt und der zugehörige, ebenfalls enthaltene [Serializer][KSerializer] an den Generator übergeben.
     *
     * Das Ergebnis wird anschließend zurückgegeben.
     */
    internal fun stringify(generator: SHDGenerator): String {
        return generator.generate(data, serializer)
    }
}