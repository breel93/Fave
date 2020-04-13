package com.fave.breezil.fave.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.fave.breezil.fave.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.databinding.QuickCategoryItemBinding

class QuickCategoryRecyclerAdapter(private var searchList:List<String>, private val seeMoreClickListener:SeeMoreClickListener)
    :RecyclerView.Adapter<QuickCategoryRecyclerAdapter.QuickSearchHolder>() {

    lateinit var binding: QuickCategoryItemBinding

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, i:Int):QuickSearchHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = QuickCategoryItemBinding.inflate(layoutInflater, parent, false)
        return QuickSearchHolder(binding)
    }
    override fun onBindViewHolder(@NonNull quickSearchHolder:QuickSearchHolder, position:Int) {
        val string = searchList[position]
        quickSearchHolder.bind(string, seeMoreClickListener)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }


    fun setList(search:List<String>) {
        searchList = search
        notifyDataSetChanged()
    }
    inner class QuickSearchHolder(internal var binding: QuickCategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(string:String, seeMoreClickListener:SeeMoreClickListener) {
            itemView.setOnClickListener { seeMoreClickListener.showMoreCategory(string) }
            binding.searchText.text = string
        }
    }
}