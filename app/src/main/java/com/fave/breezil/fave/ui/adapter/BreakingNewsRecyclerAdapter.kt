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
import com.fave.breezil.fave.databinding.BreakingNewsItemBinding
import com.fave.breezil.fave.model.Article

class BreakingNewsRecyclerAdapter(
  private val mContext: Context,
  private val articleClickListener: ArticleClickListener,
  private val articleLongClickListener: ArticleLongClickListener
) :
  ListAdapter<Article, BreakingNewsRecyclerAdapter.ChildViewHolder>(DIFF_CALLBACK) {

  internal lateinit var binding: BreakingNewsItemBinding
  internal lateinit var circularProgressDrawable: CircularProgressDrawable

  override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ChildViewHolder {
    val layoutInflater = LayoutInflater.from(viewGroup.context)
    binding = BreakingNewsItemBinding.inflate(layoutInflater, viewGroup, false)
    return ChildViewHolder(binding)
  }

  override fun onBindViewHolder(childViewHolder: ChildViewHolder, i: Int) {

    val articles = getItem(i)
    childViewHolder.bind(articles, articleClickListener, articleLongClickListener)
  }

  inner class ChildViewHolder(var binding: BreakingNewsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
      article: Article,
      articleClickListener: ArticleClickListener,
      articleLongClickListener: ArticleLongClickListener
    ) {

      itemView.setOnClickListener { articleClickListener.showDetails(article) }
      itemView.setOnLongClickListener {
        articleLongClickListener.doSomething(article)
        true
      }

      circularProgressDrawable = CircularProgressDrawable(mContext)
      circularProgressDrawable.strokeWidth = 10f
      circularProgressDrawable.centerRadius = 40f
      circularProgressDrawable.setColorSchemeColors(
        R.color.colorAccent, R.color.colorPrimary,
        R.color.colorblue, R.color.hotPink
      )
      circularProgressDrawable.start()

      Glide.with(mContext)
        .load(article.urlToImage)
        .apply(
          RequestOptions()
            .placeholder(circularProgressDrawable)
            .error(R.drawable.placeholder)
        )
        .into(binding.articleImage)

      binding.articleTitle.text = article.title
      binding.sourcesText.text = article.source!!.name
    }
  }

  companion object {
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
      override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title
      }

      override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return (oldItem.title == newItem.title &&
            oldItem.description == newItem.description &&
            oldItem.publishedAt == newItem.publishedAt)
      }
    }
  }
}
