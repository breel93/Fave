package com.fave.breezil.fave.ui.adapter

import androidx.recyclerview.widget.DiffUtil

import com.fave.breezil.fave.model.Articles
import com.fave.breezil.fave.model.Sources

class DiffUtils(private val oldSourcesList: List<Sources>, private val newSourcesList: List<Sources>) : DiffUtil.Callback() {

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
