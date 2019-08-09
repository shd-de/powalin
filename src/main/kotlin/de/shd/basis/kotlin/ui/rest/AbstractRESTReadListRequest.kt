package de.shd.basis.kotlin.ui.rest

import de.shd.basis.kotlin.ui.rest.read.limit.Range
import kotlinx.serialization.Serializable

/**
 * @see de.shd.basis.rest.crud.read.request.AbstractRESTReadListRequest
 * @author Marcel Ziganow (zim)
 */
@Serializable
abstract class AbstractRESTReadListRequest : AbstractRESTRequest() {

  /**
   * Definiert den Bereich der durch diesen Request beschriebenen Liste,
   * die tatsaechlich an den Aufrufer uebertragen werden soll
   */
  private var range: Range? = null

  fun getRange(): Range? {
    return this.range
  }

  fun setRange(range: Range?): AbstractRESTReadListRequest {
    this.range = range
    return this
  }
}