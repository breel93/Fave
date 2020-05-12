/**
 *  Designed and developed by Fave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.fave.breezil.fave.utils.helpers

import android.text.Editable
import android.text.Html.TagHandler
import android.text.Spannable
import android.text.style.StrikethroughSpan
import org.xml.sax.XMLReader

class HtmlTagHandler : TagHandler {

  override fun handleTag(
    opening: Boolean,
    tag: String,
    output: Editable,
    xmlReader: XMLReader
  ) {
    if (tag.equals("strike", ignoreCase = true) || tag == "s") {
      processStrike(opening, output)
    }
  }

  private fun processStrike(opening: Boolean, output: Editable) {
    val len = output.length
    if (opening) {
      output.setSpan(StrikethroughSpan(), len, len, Spannable.SPAN_MARK_MARK)
    } else {
      val obj = getLast(output, StrikethroughSpan::class.java)
      val where = output.getSpanStart(obj)

      output.removeSpan(obj)

      if (where != len) {
        output.setSpan(StrikethroughSpan(), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
      }
    }
  }

  private fun getLast(text: Editable, kind: Class<*>): Any? {
    val objs = text.getSpans<Any>(0, text.length, kind as Class<Any>?)

    if (objs.isEmpty()) {
      return null
    } else {
      for (i in objs.size downTo 1) {
        if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
          return objs[i - 1]
        }
      }
      return null
    }
  }
}
