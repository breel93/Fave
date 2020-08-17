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
import androidx.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentSearchBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.repository.NetworkState
import com.fave.breezil.fave.ui.adapter.ArticleRecyclerViewAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.ui.main.look_up.LookUpViewModel
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_TYPE
import com.fave.breezil.fave.utils.Constant.Companion.sourcesPreferenceList
import com.fave.breezil.fave.utils.Constant.Companion.todayDate
import com.fave.breezil.fave.utils.Constant.Companion.twoDaysAgoDate
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : DaggerFragment() {

  lateinit var binding: FragmentSearchBinding

  internal var adapter: ArticleRecyclerViewAdapter? = null
  private lateinit var viewModel: LookUpViewModel

  private var sortBy: String? = null
  private var source: String = ""

  private lateinit var sharedPreferences: SharedPreferences

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
    viewModel = ViewModelProvider(this, viewModelFactory).get(LookUpViewModel::class.java)
    sortBy = sharedPreferences.getString(getString(R.string.pref_everything_sort_by_key), getString(R.string.blank))
    binding.shimmerViewContainer.startShimmer()
    setUpAdapter()
    setUpViewModel()
    search()
    binding.swipeRefresh.setOnRefreshListener { setUpViewModel() }
    return binding.root
  }

  private fun search() {
    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

      override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
          refresh(query)
        }
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
          refresh(newText)
        }
        return true
      }
    })
  }

  private fun setUpAdapter() {
    val articleClickListener = object : ArticleClickListener {
      override fun showDetails(article: Article) {
        val descriptionBottomSheetFragment = DescriptionBottomSheetFragment.getArticles(article)
        descriptionBottomSheetFragment.show(requireActivity().supportFragmentManager, getString(R.string.show))
      }
    }
    val articleLongClickListener = object : ArticleLongClickListener {
      override fun doSomething(article: Article) {
        val actionBottomSheetFragment = ActionBottomSheetFragment.getArticles(article, ARTICLE_TYPE)
        actionBottomSheetFragment.show(requireActivity().supportFragmentManager, getString(R.string.show))
      }
    }
    val layoutManager = GridLayoutManager(requireContext(), 2)
    adapter = ArticleRecyclerViewAdapter(requireContext(), articleClickListener, articleLongClickListener)
    layoutManager.spanSizeLookup = object : SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return when (adapter!!.getItemViewType(position)) {
          ArticleRecyclerViewAdapter.TYPE_HEADER -> 2
          ArticleRecyclerViewAdapter.TYPE_ITEM -> 1
          else -> 1
        }
      }
    }
    binding.articleSearchList.layoutManager = layoutManager
    binding.articleSearchList.adapter = adapter
  }

  private fun setUpViewModel() {
    setupLoading()
    binding.swipeRefresh.visibility = View.VISIBLE
    binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.hotPink)
    if (!isAdded) {
      return
    }
    viewModel.setParameter(
      getString(R.string.blank),
      sourcesPreferenceList(requireContext(), sharedPreferences),
      sortBy!!,
      todayDate,
      twoDaysAgoDate,
      getString(R.string.blank)
    )
    viewModel.refreshArticle().observe(viewLifecycleOwner, Observer {
      it?.let {
        adapter!!.submitList(it)
        binding.shimmerViewContainer.visibility = View.GONE
        if (it.size > 0) {
          adapter!!.setFirstArticle(it[1]!!)
        }
      }
    })
    val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
    val editor = preferences.edit()
    editor.putString(getString(R.string.source), source)
    editor.apply()
    binding.swipeRefresh.let { binding.swipeRefresh.isRefreshing = false }
  }

  private fun refresh(search: String) {
    setupLoading()
    viewModel.setParameter(
      search,
      sourcesPreferenceList(requireContext(), sharedPreferences),
      sortBy!!, todayDate, twoDaysAgoDate, getString(R.string.blank)
    )
    viewModel.refreshArticle().observe(viewLifecycleOwner, Observer {
      it?.let {
        adapter!!.submitList(it)
        binding.shimmerViewContainer.visibility = View.GONE
      }
    })
  }

  private fun setupLoading(){
    viewModel.getNetworkState().observe(viewLifecycleOwner, Observer {
      if (it != null) {
        when (it.status) {
          NetworkState.Status.SUCCESS -> {
            binding.shimmerViewContainer.visibility = View.GONE
            binding.searchError.visibility = View.GONE
            binding.responseError.visibility = View.GONE
            binding.articleSearchList.visibility = View.VISIBLE
          }
          NetworkState.Status.FAILED -> {
            binding.shimmerViewContainer.visibility = View.GONE
            binding.searchError.visibility= View.GONE
            binding.responseError.visibility = View.VISIBLE
            binding.articleSearchList.visibility = View.GONE
          }
          NetworkState.Status.NO_RESULT -> {
            binding.shimmerViewContainer.visibility = View.GONE
            binding.searchError.visibility = View.VISIBLE
            binding.responseError.visibility = View.GONE
            binding.articleSearchList.visibility = View.GONE
          }
          NetworkState.Status.RUNNING -> {
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.searchError.visibility = View.GONE
            binding.responseError.visibility = View.GONE
          }
          else -> {

          }
        }
      }
    })
    viewModel.getNetworkState().observe(viewLifecycleOwner, Observer { networkState ->
      networkState?.let { adapter!!.setNetworkState(networkState) }
    })

  }
}
