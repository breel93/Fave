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

import androidx.recyclerview.widget.DiffUtil

import com.fave.breezil.fave.model.Sources

class DiffUtils(
  private val oldSourcesList: List<Sources>,
  private val newSourcesList: List<Sources>
) : DiffUtil.Callback() {

  override fun getOldListSize(): Int {
    return oldSourcesList.size
  }

  override fun getNewListSize(): Int {
    return newSourcesList.size
  }

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldSourcesList[oldItemPosition].id === newSourcesList[newItemPosition].id
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val oldArticle = oldSourcesList[oldItemPosition]
    val newArticle = newSourcesList[newItemPosition]

    return oldArticle.name == newArticle.name
  }

  override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
    return super.getChangePayload(oldItemPosition, newItemPosition)
  }
}
