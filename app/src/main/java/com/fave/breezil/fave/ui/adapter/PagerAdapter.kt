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
package com.fave.breezil.fave.ui.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
  private var fragmentList = SparseArray<Fragment>()
  private var mainFragmentList = ArrayList<Fragment>()

  override fun getItem(position: Int): Fragment {
    return mainFragmentList[position]
  }

  override fun getCount(): Int {
    return mainFragmentList.size
  }

  fun addFragments(fragment: Fragment) {
    mainFragmentList.add(fragment)
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val fragment = super.instantiateItem(container, position) as Fragment
    fragmentList.put(position, fragment)
    return fragment
  }

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    fragmentList.remove(position)
    super.destroyItem(container, position, `object`)
  }
}
