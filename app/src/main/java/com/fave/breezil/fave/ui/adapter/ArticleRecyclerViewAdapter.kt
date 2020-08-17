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
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fave.breezil.fave.R
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.databinding.ArticleItemBinding
import com.fave.breezil.fave.databinding.BreakingNewsItemBinding
import com.fave.breezil.fave.databinding.ItemNetworkStateBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.repository.NetworkState
import com.fave.breezil.fave.utils.Constant.Companion.ONE
import com.fave.breezil.fave.utils.Constant.Companion.THREE
import com.fave.breezil.fave.utils.Constant.Companion.TWO
import com.fave.breezil.fave.utils.Constant.Companion.ZERO
import com.fave.breezil.fave.utils.getTimeAgo
import com.fave.breezil.fave.utils.helpers.HtmlTagHandler
import java.util.*

class ArticleRecyclerViewAdapter(
  internal var context: Context,
  private val articleClickListener: ArticleClickListener,
  private val articleLongClickListener: ArticleLongClickListener
) :
  PagedListAdapter<Article, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

  internal lateinit var circularProgressDrawable: CircularProgressDrawable
  private var networkState: NetworkState? = null
  private var article: Article? = null

  internal lateinit var binding: ArticleItemBinding
  private var networkStateBinding: ItemNetworkStateBinding? = null
  private lateinit var headerBinding: BreakingNewsItemBinding

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    return when (viewType) {
      TYPE_PROGRESS -> {
        networkStateBinding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false)
        NetworkStateItemViewHolder(networkStateBinding!!)
      }
      TYPE_HEADER -> {
        headerBinding = BreakingNewsItemBinding.inflate(layoutInflater, parent, false)
        HeadListViewHolder(headerBinding)
      }
      else -> {
        binding = ArticleItemBinding.inflate(layoutInflater, parent, false)

        ArticleHolder(binding)
      }
    }
  }

  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

    when (viewHolder) {
      is ArticleHolder -> {
        val article = getItem(position)

        viewHolder.bind(article, articleClickListener, articleLongClickListener)
      }
      is HeadListViewHolder -> {
        val article = getItem(position)
        viewHolder.bind(article, articleClickListener, articleLongClickListener)
      }
      else -> {
        (viewHolder as NetworkStateItemViewHolder).bindView(networkState)
      }
    }
  }

  private fun hasExtraRow(): Boolean {
    return networkState != null && networkState != NetworkState.LOADED
  }

  override fun getItemViewType(position: Int): Int {
    return if (hasExtraRow() && position == itemCount - ONE) {
      TYPE_PROGRESS
    } else {
      if(position< ONE){
        TYPE_HEADER
      }else{
        TYPE_ITEM
      }
    }
  }

  fun setFirstArticle(article: Article){
    this.article = article
  }
  fun setNetworkState(newNetworkState: NetworkState) {
    val previousState = this.networkState
    val previousExtraRow = hasExtraRow()
    this.networkState = newNetworkState
    val newExtraRow = hasExtraRow()
    if (previousExtraRow != newExtraRow) {
      if (previousExtraRow) {
        notifyItemRemoved(itemCount)
      } else {
        notifyItemInserted(itemCount)
      }
    } else if (newExtraRow && previousState != newNetworkState) {
      notifyItemChanged(itemCount - ONE)
    }
  }


  internal inner class ArticleHolder(var binding: ArticleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
      article: Article?,
      articleClickListener: ArticleClickListener,
      articleLongClickListener: ArticleLongClickListener
    ) {
      itemView.setOnClickListener { articleClickListener.showDetails(article!!) }
      itemView.setOnLongClickListener {
        articleLongClickListener.doSomething(article!!)
        true
      }

      if (article!!.title != null) {
        binding.articleTitle.text = Html.fromHtml(article.title, null, HtmlTagHandler())
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

      if (article.source != null) {
        binding.sourcesText.text = article.source!!.name
      }
      binding.publishedAt.text = article.publishedAt!!.asTimeAgo(context.resources) + " |"
    }
  }

  inner class HeadListViewHolder internal constructor(private val binding: BreakingNewsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
      article: Article?,
      articleClickListener: ArticleClickListener,
      articleLongClickListener: ArticleLongClickListener
    ) {
      itemView.setOnClickListener { articleClickListener.showDetails(article!!) }
      itemView.setOnLongClickListener {
        articleLongClickListener.doSomething(article!!)
        true
      }

      if (article!!.title != null) {
        binding.articleTitle.text = Html.fromHtml(article.title, null, HtmlTagHandler())
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



      if (article.source != null) {
        binding.sourcesText.text = article.source!!.name
      }
      binding.timeCreated.text = article.publishedAt!!.asTimeAgo(context.resources) + " |"
    }
  }


  inner class NetworkStateItemViewHolder internal constructor(private val binding: ItemNetworkStateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    internal fun bindView(networkState: NetworkState?) {
      if (networkState != null && networkState.status === NetworkState.Status.RUNNING) {
        binding.progressBar.visibility = View.VISIBLE
      } else {
        binding.progressBar.visibility = View.GONE
      }

      if (networkState != null && networkState.status === NetworkState.Status.FAILED) {
        binding.errorMsg.visibility = View.VISIBLE
      } else {
        binding.errorMsg.visibility = View.GONE
      }
    }
  }

  private fun Date.asTimeAgo(resources: Resources): String {
    return getTimeAgo(this.time, System.currentTimeMillis(), resources)
  }
  companion object {

    const val TYPE_PROGRESS = ZERO
    const val TYPE_ITEM = ONE
    const val TYPE_HEADER = TWO

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
