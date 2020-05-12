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
package com.fave.breezil.fave.ui.main.sources

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuInflater
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.fave.breezil.fave.R
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.databinding.FragmentSourceDetailBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.ui.adapter.ArticleRecyclerViewAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.main.look_up.LookUpViewModel
import com.fave.breezil.fave.utils.Constant.Companion.SOURCENAME
import com.fave.breezil.fave.utils.Constant.Companion.todayDate
import com.fave.breezil.fave.utils.Constant.Companion.twoDaysAgoDate
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class SourceDetailFragment : DaggerFragment() {

  lateinit var binding: FragmentSourceDetailBinding

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private var articleAdapter: ArticleRecyclerViewAdapter? = null
  lateinit var viewModel: LookUpViewModel

  private val sources: Sources?
    get() = if (arguments!!.getParcelable<Sources>(SOURCENAME) != null) {
      arguments!!.getParcelable(SOURCENAME)
    } else {
      null
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_source_detail, container, false)
    binding.sourceArticleList.setHasFixedSize(true)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(LookUpViewModel::class.java)

    setHasOptionsMenu(true)
    setUpAdapter()
    setUpViewModel(sources!!.id)

    binding.swipeRefresh.setOnRefreshListener { setUpViewModel(sources!!.id) }
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
        val actionBottomSheetFragment = ActionBottomSheetFragment.getArticles(article)
        actionBottomSheetFragment.show(childFragmentManager, getString(R.string.show))
      }
    }

    articleAdapter =
      ArticleRecyclerViewAdapter(context!!, articleClickListener, articleLongClickListener)
    binding.sourceArticleList.adapter = articleAdapter
  }

  private fun setUpViewModel(source: String) {
    binding.swipeRefresh.visibility = View.VISIBLE
    binding.swipeRefresh.setColorSchemeResources(
      R.color.colorAccent, R.color.colorPrimary,
      R.color.colorblue, R.color.hotPink
    )

    viewModel.setParameter(
      getString(R.string.blank),
      source,
      "",
      todayDate,
      twoDaysAgoDate,
      getString(R.string.blank)
    )
    viewModel.articleList.observe(viewLifecycleOwner, Observer {
      it?.let { articleAdapter!!.submitList(it) }
    })
    viewModel.getNetworkState().observe(viewLifecycleOwner, Observer { networkState ->
//      if (networkState != null) {
//        articleAdapter!!.setNetworkState(networkState)
//      }
      networkState?.let { articleAdapter!!.setNetworkState(networkState) }
    })

    if (binding.swipeRefresh != null) {
      binding.swipeRefresh.isRefreshing = false
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    menu.clear()
    inflater.inflate(R.menu.second_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  companion object {
    fun getSource(sources: Sources): SourceDetailFragment {
      val fragment = SourceDetailFragment()
      val args = Bundle()
      args.putParcelable(SOURCENAME, sources)
      fragment.arguments = args
      return fragment
    }
  }
}
