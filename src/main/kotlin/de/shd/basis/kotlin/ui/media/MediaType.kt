package de.shd.basis.kotlin.ui.media

/**
 * Jeder Wert dieses Enums repräsentiert einen [Internet Media Type](https://de.wikipedia.org/wiki/Internet_Media_Type) (auch bekannt als "MIME Type"
 * oder "Content Type"), der u.a. dazu genutzt wird, um anzugeben, in was für einem Format Daten übertragen werden. Dies können sowohl textuelle als
 * auch Binärformate sein.
 *
 * @author Florian Steitz (fst)
 */
enum class MediaType(val type: String) {

    /**
     * Repräsentiert den Media Type `application/json`.
     */
    APPLICATION_JSON("application/json")
}