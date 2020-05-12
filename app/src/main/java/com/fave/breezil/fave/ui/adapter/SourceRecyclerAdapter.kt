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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fave.breezil.fave.databinding.SourceItemBinding
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.ui.callbacks.SourcesClickListener

class SourceRecyclerAdapter(private val sourcesClickListener: SourcesClickListener) :
  ListAdapter<Sources, SourceRecyclerAdapter.SourceHolder>(DIFF_CALLBACK) {
  internal lateinit var binding: SourceItemBinding

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    binding = SourceItemBinding.inflate(layoutInflater, parent, false)
    return SourceHolder(binding)
  }

  override fun onBindViewHolder(holder: SourceHolder, position: Int) {
    val sources = getItem(position)
    holder.bind(sources, sourcesClickListener)
  }

  inner class SourceHolder(var binding: SourceItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(sources: Sources, sourcesClickListener: SourcesClickListener) {
      itemView.setOnClickListener { sourcesClickListener.showDetails(sources) }

      binding.sourcesTitle.text = sources.name

      binding.sourceDescriptions.text = sources.description
    }
  }

  companion object {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Sources>() {
      override fun areItemsTheSame(oldItem: Sources, newItem: Sources): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Sources, newItem: Sources): Boolean {
        return (oldItem.name == newItem.name &&
            oldItem.description == newItem.description &&
            oldItem.url == newItem.url)
      }
    }
  }
}
