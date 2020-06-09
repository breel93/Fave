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
package com.fave.breezil.fave.ui.main.top_stories

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fave.breezil.fave.R
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.ui.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.databinding.FragmentCategoryArticlesBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.ui.adapter.ArticleRecyclerViewAdapter
import com.fave.breezil.fave.ui.adapter.QuickCategoryRecyclerAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_TYPE
import com.fave.breezil.fave.utils.Constant.Companion.CATEGORYNAME
import dagger.android.support.DaggerFragment
import java.util.Collections
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */

class CategoryArticlesFragment : DaggerFragment() {

  lateinit var binding: FragmentCategoryArticlesBinding

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private var articleAdapter: ArticleRecyclerViewAdapter? = null
  lateinit var viewModel: MainViewModel

  private var country: String? = null

  lateinit var sharedPreferences: SharedPreferences

  private val category: String?
    get() = if (arguments!!.getString(CATEGORYNAME) != null) {
      arguments!!.getString(CATEGORYNAME)
    } else {
      null
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_articles, container, false)
    viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
    country =
      sharedPreferences.getString(getString(R.string.country_key), getString(R.string.blank))

    setUpAdapter()
    setUpViewModel(category!!)
    goBack()

    binding.shimmerViewContainer.startShimmer()
    binding.swipeRefresh.setOnRefreshListener { setUpViewModel(category!!) }

    return binding.root
  }


  private fun setUpAdapter() {

    val articleClickListener = object : ArticleClickListener {
      override fun showDetails(article: Article) {
        val descriptionBottomSheetFragment = DescriptionBottomSheetFragment.getArticles(article)
        descriptionBottomSheetFragment.show(childFragmentManager, getString(R.string.show))
      }
    }
    val articleLongClickListener = object : ArticleLongClickListener {
      override fun doSomething(article: Article) {
        val actionBottomSheetFragment = ActionBottomSheetFragment.getArticles(article, ARTICLE_TYPE)
        actionBottomSheetFragment.show(childFragmentManager, getString(R.string.show))
      }
    }
    articleAdapter =
      ArticleRecyclerViewAdapter(context!!, articleClickListener, articleLongClickListener)
    val layoutGridManager = GridLayoutManager(context, 2)

    layoutGridManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return when (articleAdapter!!.getItemViewType(position)) {
          ArticleRecyclerViewAdapter.TYPE_HEADER -> 2
          ArticleRecyclerViewAdapter.TYPE_ITEM -> 1
          else -> 1
        }
      }
    }
    binding.articleCategoryList.layoutManager = layoutGridManager
  }

  private fun setUpViewModel(category: String) {
    binding.categoryText.text = category
    binding.swipeRefresh.visibility = View.VISIBLE
    binding.swipeRefresh.setColorSchemeResources(
      R.color.colorAccent, R.color.colorPrimary,
      R.color.colorblue, R.color.hotPink
    )

    viewModel.articleList.removeObservers(viewLifecycleOwner)

    viewModel.setParameter(
      country!!,
      getString(R.string.blank),
      category,
      getString(R.string.blank)
    )
    viewModel.articleList.observe(viewLifecycleOwner, Observer {
      if (it != null) {
        articleAdapter!!.submitList(it)
        binding.articleCategoryList.adapter = articleAdapter
        articleAdapter!!.notifyDataSetChanged()
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.GONE
        if(it.size > 0){
          articleAdapter!!.setFirstArticle(it[1]!!)
        }
      }
    })
    viewModel.getNetworkState().observe(viewLifecycleOwner, Observer { networkState ->
      if (networkState != null) {
        articleAdapter!!.setNetworkState(networkState)
      }
    })

    if (binding.swipeRefresh != null) {
      binding.swipeRefresh.isRefreshing = false
    }
  }


  companion object {
    fun getCategory(category: String): CategoryArticlesFragment {
      val fragment = CategoryArticlesFragment()
      val args = Bundle()
      args.putString(CATEGORYNAME, category)
      fragment.arguments = args
      return fragment
    }
  }

  private fun goBack(){
    binding.backPressed.setOnClickListener{
      fragmentManager!!.popBackStack();
    }
  }

}
