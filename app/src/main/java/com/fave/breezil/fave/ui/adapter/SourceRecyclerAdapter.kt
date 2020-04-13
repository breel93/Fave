package com.fave.breezil.fave.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

import com.fave.breezil.fave.callbacks.SourcesClickListener
import com.fave.breezil.fave.databinding.SourceItemBinding
import com.fave.breezil.fave.model.Sources

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



    inner class SourceHolder(var binding: SourceItemBinding) :RecyclerView.ViewHolder(binding.root) {
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
                return (oldItem.name == newItem.name
                        && oldItem.description == newItem.description
                        && oldItem.url == newItem.url)
            }
        }
    }


}
