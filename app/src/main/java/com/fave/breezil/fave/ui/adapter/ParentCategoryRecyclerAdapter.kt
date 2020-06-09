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
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.fave.breezil.fave.databinding.BreakingNewsListBinding
import com.fave.breezil.fave.databinding.ParentMainItemBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.ParentModel
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.ui.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.utils.Constant.Companion.ONE
import com.fave.breezil.fave.utils.Constant.Companion.ZERO
import kotlin.collections.ArrayList

class ParentCategoryRecyclerAdapter(
  private val mContext: Context,
  private val articleClickListener: ArticleClickListener,
  private val articleLongClickListener: ArticleLongClickListener,
  private val seeMoreClickListener: SeeMoreClickListener)
  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  internal lateinit var binding: ParentMainItemBinding
  private lateinit var breakingNewBinding: BreakingNewsListBinding
  private var mParentList = ArrayList<ParentModel>()

  private var mArticles = ArrayList<Article>()
  val duration = 10
  val pixelsToMove = 30
  private val mHandler = Handler(Looper.getMainLooper())

  override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
    val layoutInflater = LayoutInflater.from(viewGroup.context)
    return if (i == TYPE_BREAKING_NEWS) {
      breakingNewBinding = BreakingNewsListBinding.inflate(layoutInflater, viewGroup, false)
      BreakingNewsViewHolder(breakingNewBinding)
    } else {
      binding = ParentMainItemBinding.inflate(layoutInflater, viewGroup, false)
      ParentViewHolder(binding)
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (holder is ParentViewHolder) {
      val parentModel = mParentList[position]
      holder.bind(parentModel, seeMoreClickListener)
    } else {
      (holder as BreakingNewsViewHolder).bind(mArticles)
    }
  }

  override fun getItemCount(): Int {
    return mParentList.size
  }

  override fun getItemViewType(position: Int): Int {
    return if (position < ONE) {
      TYPE_BREAKING_NEWS
    } else {
      TYPE_ITEM
    }
  }

  fun setBreakingNews(articleList: ArrayList<Article>) {
    mArticles = articleList
  }

  fun setList(parentModelArrayList: ArrayList<ParentModel>) {
    mParentList = parentModelArrayList
  }

  inner class ParentViewHolder(var binding: ParentMainItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(parentModel: ParentModel, seeMoreClickListener: SeeMoreClickListener) {
      binding.moreItemText.setOnClickListener { seeMoreClickListener.showMoreCategory(parentModel.title!!) }
      binding.parentTitleText.text = parentModel.title

      val articles = parentModel.articles

      val homepageCategoryRecyclerViewAdapter = HomepageCategoryRecyclerViewAdapter(
        mContext,
        articleClickListener, articleLongClickListener
      )
      binding.parentRecyclerView.setHasFixedSize(true)
      binding.parentRecyclerView.layoutManager = LinearLayoutManager(
        mContext,
        LinearLayoutManager.VERTICAL, false
      )
      binding.parentRecyclerView.adapter = homepageCategoryRecyclerViewAdapter
      homepageCategoryRecyclerViewAdapter.submitList(articles)

      binding.parentRecyclerView.isNestedScrollingEnabled = true
    }
  }

  inner class BreakingNewsViewHolder(private var binding: BreakingNewsListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    internal fun bind(articles: List<Article>) {
      val breakingNewsRecyclerAdapter = BreakingNewsRecyclerAdapter(
        mContext,
        articleClickListener, articleLongClickListener
      )
      binding.breakingNewsList.setHasFixedSize(true)
      val linearLayoutManager = LinearLayoutManager(
        mContext,
        LinearLayoutManager.HORIZONTAL, false
      )
      binding.breakingNewsList.layoutManager = linearLayoutManager
      binding.breakingNewsList.adapter = breakingNewsRecyclerAdapter
      breakingNewsRecyclerAdapter.submitList(articles)

      val time = 10000 // it's the delay time for sliding between items in recyclerview

      // The LinearSnapHelper will snap the center of the target child view to the center of the attached RecyclerView ,
      // it's optional if you want , you can use it
      val linearSnapHelper = LinearSnapHelper()
      if (binding.breakingNewsList.onFlingListener == null)
        linearSnapHelper.attachToRecyclerView(binding.breakingNewsList)

    }
  }
  companion object {
    private const val TYPE_BREAKING_NEWS = ZERO
    private const val TYPE_ITEM = ONE
  }
}
