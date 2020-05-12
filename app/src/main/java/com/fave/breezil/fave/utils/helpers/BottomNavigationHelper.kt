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

import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

/**
 * Created by breezil on 1/2/2018.
 */

object BottomNavigationHelper {
  fun disableShiftMode(view: BottomNavigationView) {
    val menuView = view.getChildAt(0) as BottomNavigationMenuView
    try {
      val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
      shiftingMode.isAccessible = true
      shiftingMode.setBoolean(menuView, false)
      shiftingMode.isAccessible = false
      for (i in 0 until menuView.childCount) {
        val item = menuView.getChildAt(i) as BottomNavigationItemView

        item.setShifting(false)
        // set once again checked value, so view will be updated

        item.setChecked(item.itemData.isChecked)
      }
    } catch (e: NoSuchFieldException) {
      Timber.e(e, "Unable to get shift mode field")
    } catch (e: IllegalAccessException) {
      Timber.e(e, "Unable to change value of shift mode")
    }
  }
}
