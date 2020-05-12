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

import android.view.View

import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class FadeOutTransformation : ViewPager.PageTransformer {
  override fun transformPage(page: View, position: Float) {
//        page.translationX = -position * page.width
//        page.alpha = 1 - abs(position)
    if (position <= -1.0F || position >= 1.0F) {
      page.translationX = page.width * position
      page.alpha = 0.0F
    } else if (position == 0.0F) {
      page.translationX = page.width * position
      page.alpha = 1.0F
    } else {
      // position is between -1.0F & 0.0F OR 0.0F & 1.0F
      page.translationX = page.width * -position
      page.alpha = 1.0F - abs(position)
    }
  }
}
