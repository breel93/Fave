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

import android.content.Context
import android.content.res.Resources
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fave.breezil.fave.R
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.databinding.ArticleItemBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.utils.getTimeAgo
import com.fave.breezil.fave.utils.helpers.HtmlTagHandler
import java.util.*

class BookMarkRecyclerAdapter(
  internal var context: Context,
  private val listener: ArticleClickListener,
  private val longClickListener: ArticleLongClickListener
) :
  ListAdapter<Article, BookMarkRecyclerAdapter.BookMarkHolder>(DIFF_CALLBACK) {
  internal lateinit var binding: ArticleItemBinding
  internal lateinit var circularProgressDrawable: CircularProgressDrawable

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    binding = ArticleItemBinding.inflate(layoutInflater, parent, false)
    return BookMarkHolder(binding)
  }

  override fun onBindViewHolder(holder: BookMarkHolder, position: Int) {
    val bookMark = getItem(position)
    holder.bind(bookMark, listener, longClickListener)
  }

  inner class BookMarkHolder(var binding: ArticleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
      article: Article,
      listener: ArticleClickListener,
      longClickListener: ArticleLongClickListener
    ) {
      itemView.setOnClickListener { listener.showDetails(article) }
      itemView.setOnLongClickListener {
        longClickListener.doSomething(article)
        true
      }
      circularProgressDrawable = CircularProgressDrawable(context)
      circularProgressDrawable.strokeWidth = 12f
      circularProgressDrawable.centerRadius = 60f
      circularProgressDrawable.setColorSchemeColors(
        R.color.colorAccent, R.color.colorPrimary,
        R.color.colorblue, R.color.hotPink
      )
      circularProgressDrawable.start()
      Glide.with(context)
        .load(article.urlToImage)
        .apply(
          RequestOptions()
            .placeholder(circularProgressDrawable)
            .error(R.drawable.placeholder)
        )
        .into(binding.articleImage)
      binding.sourcesText.text = article.source!!.name
      binding.articleTitle.text = Html.fromHtml(article.title, null, HtmlTagHandler())
      binding.publishedAt.text = article.publishedAt!!.asTimeAgo(context.resources) + " |"
    }
  }
  private fun Date.asTimeAgo(resources: Resources): String {
    return getTimeAgo(this.time, System.currentTimeMillis(), resources)
  }
  companion object {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
      override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
      }
    }
  }
}
