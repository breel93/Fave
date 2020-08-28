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

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentCategoryArticlesBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.repository.NetworkState
import com.fave.breezil.fave.ui.adapter.ArticleRecyclerViewAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.ui.callbacks.FragmentOpenedListener
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_TYPE
import com.fave.breezil.fave.utils.Constant.Companion.CATEGORYNAME
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */

@AndroidEntryPoint
class CategoryArticlesFragment : Fragment() {

  lateinit var binding: FragmentCategoryArticlesBinding

  private var articleAdapter: ArticleRecyclerViewAdapter? = null
  private val viewModel: MainViewModel by viewModels()
  private lateinit var openedListener: FragmentOpenedListener

  private var country: String? = null

  lateinit var sharedPreferences: SharedPreferences

  private val category: String?
    get() = if (requireArguments().getString(CATEGORYNAME) != null) {
      requireArguments().getString(CATEGORYNAME)
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

    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
    country =
      sharedPreferences.getString(getString(R.string.country_key), getString(R.string.blank))
    setUpAdapter()
    setUpViewModel(category!!)
    goBack()
    openedListener.isOpened(true)
    binding.shimmerViewContainer.startShimmer()
    binding.swipeRefresh.setOnRefreshListener { setUpViewModel(category!!) }

    return binding.root
  }
  override fun onAttach(context: Context) {
    super.onAttach(context)
    openedListener = try {
      requireActivity() as FragmentOpenedListener
    } catch (e: ClassCastException) {
      throw ClassCastException(
        context.toString()
            + " must implement FragmentOpenedListener "
      )
    }
  }

  private fun setUpAdapter() {
    binding.shimmerViewContainer.startShimmer()
    binding.shimmerViewContainer.visibility = View.VISIBLE
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
      ArticleRecyclerViewAdapter(requireContext(), articleClickListener, articleLongClickListener)
    val layoutGridManager = GridLayoutManager(requireContext(), 2)
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
    binding.articleCategoryList.adapter = articleAdapter
  }

  private fun setUpViewModel(category: String) {
    setupLoading()
    binding.categoryText.text = category
    binding.swipeRefresh.visibility = View.VISIBLE
    binding.swipeRefresh.setColorSchemeResources(
      R.color.colorAccent, R.color.hotPink
    )
    viewModel.setParameter(country!!, getString(R.string.blank), category, getString(R.string.blank))
    viewModel.articleList.observe(viewLifecycleOwner, Observer {
      it?.let {
        articleAdapter!!.submitList(it)
        binding.shimmerViewContainer.visibility = View.GONE
        if(it.size > 0){
          articleAdapter!!.setFirstArticle(it[1]!!)
        }
      }
    })
    binding.swipeRefresh.let {
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
      requireActivity().supportFragmentManager.popBackStack()
    }
  }
  override fun onDestroy() {
    super.onDestroy()
    openedListener.isOpened(false)
  }

  private fun setupLoading(){
    viewModel.initialLoadingState.observe(viewLifecycleOwner, Observer {
      if(it.status == NetworkState.Status.RUNNING){
        binding.shimmerViewContainer.visibility = View.VISIBLE
        binding.shimmerViewContainer.startShimmer()
      }else if(it.status == NetworkState.Status.SUCCESS){
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.GONE
      }
    })
    viewModel.getNetworkState().observe(viewLifecycleOwner, Observer {
      it?.let {
        when (it.status) {
          NetworkState.Status.SUCCESS -> {
            binding.searchError.visibility = View.GONE
            binding.responseError.visibility = View.GONE
            binding.articleCategoryList.visibility = View.VISIBLE
          }
          NetworkState.Status.FAILED -> {
            binding.searchError.visibility= View.GONE
            binding.responseError.visibility = View.VISIBLE
            binding.articleCategoryList.visibility = View.GONE
          }
          NetworkState.Status.NO_RESULT -> {
//            binding.searchError.visibility = View.VISIBLE
//            binding.responseError.visibility = View.GONE
//            binding.articleCategoryList.visibility = View.GONE
          }
          NetworkState.Status.RUNNING -> {
            binding.searchError.visibility = View.GONE
            binding.responseError.visibility = View.GONE
          }
        }
        articleAdapter!!.setNetworkState(it)
      }
    })
  }

}
