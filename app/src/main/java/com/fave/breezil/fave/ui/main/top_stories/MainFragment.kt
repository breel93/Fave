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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentMainBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.ParentModel
import com.fave.breezil.fave.ui.adapter.ParentCategoryRecyclerAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.ui.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_TYPE
import com.fave.breezil.fave.utils.Constant.Companion.getCountry
import com.fave.breezil.fave.utils.Constant.Companion.sourcesPreferenceList
import com.fave.breezil.fave.utils.Constant.Companion.todayDate
import com.fave.breezil.fave.utils.Constant.Companion.twoDaysAgoDate
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  lateinit var binding: FragmentMainBinding
  private lateinit var parentCategoryRecyclerAdapter: ParentCategoryRecyclerAdapter
  private var mArrayList = ArrayList<ParentModel>()

  private lateinit var viewModel: MainViewModel

  private var country: String? = null
  private var sortBy: String? = null

  private lateinit var sharedPreferences: SharedPreferences

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    country = getCountry(requireContext(), sharedPreferences)
    sortBy = sharedPreferences.getString(
      getString(R.string.pref_everything_sort_by_key),
      getString(R.string.blank)
    )

    viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

    binding.shimmerViewContainer.startShimmer()

    binding.swipeRefresh.setOnRefreshListener { setupViewModel() }

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupAdapter()
    setupViewModel()
  }

  private fun setupAdapter() {
    val articleClickListener = object : ArticleClickListener {
      override fun showDetails(article: Article) {
        val descriptionBottomSheetFragment = DescriptionBottomSheetFragment.getArticles(article)
        descriptionBottomSheetFragment.show(childFragmentManager, getString(R.string.show))
      }
    }
    val articleLongClickListener = object : ArticleLongClickListener {
      override fun doSomething(article: Article) {
        val actionBottomSheetFragment = ActionBottomSheetFragment.getArticles(article,
          ARTICLE_TYPE
        )
        actionBottomSheetFragment.show(childFragmentManager, getString(R.string.show))
      }
    }
    val seeMoreClickListener = object : SeeMoreClickListener {
      override fun showMoreCategory(category: String) {
        val fragment = CategoryArticlesFragment.getCategory(category)
        fragmentManager!!.beginTransaction()
          .setCustomAnimations(
            R.anim.fragment_slide_in,
            R.anim.fragment_slide_out,
            R.anim.fragment_pop_slide_in,
            R.anim.fragment_pop_slide_out
          )
          .add(R.id.parent_container, fragment)
          .hide(MainFragment())
          .addToBackStack("fragment")
          .commit()
      }
    }
    parentCategoryRecyclerAdapter = ParentCategoryRecyclerAdapter(
      context!!,
      articleClickListener,
      articleLongClickListener,
      seeMoreClickListener
    )
  }

  private fun setupViewModel() {
    binding.swipeRefresh.visibility = View.VISIBLE
    binding.swipeRefresh.setColorSchemeResources(
      R.color.colorAccent, R.color.colorPrimary,
      R.color.colorblue, R.color.hotPink
    )
    viewModel.getCategoryList(context!!, mArrayList, country!!, "", "")
      ?.observe(viewLifecycleOwner, Observer {
        if (it && ::parentCategoryRecyclerAdapter.isInitialized) {
          binding.mainRecyclerView.adapter = parentCategoryRecyclerAdapter
          parentCategoryRecyclerAdapter.setList(mArrayList)

          parentCategoryRecyclerAdapter.notifyDataSetChanged()
          binding.shimmerViewContainer.stopShimmer()
          binding.shimmerViewContainer.visibility = View.GONE
        }
      })

    viewModel.getBreakingNewList(
      sourcesPreferenceList(context!!, sharedPreferences),
      sortBy,
      todayDate,
      twoDaysAgoDate,
      getString(R.string.blank)
    ).observe(viewLifecycleOwner, Observer {
      it?.let {
        parentCategoryRecyclerAdapter.setBreakingNews(it as ArrayList<Article>)
        parentCategoryRecyclerAdapter.notifyDataSetChanged()
      }
    })
    binding.swipeRefresh.let {
      binding.swipeRefresh.isRefreshing = false
    }

    if (binding.swipeRefresh != null) {
      binding.swipeRefresh.isRefreshing = false
    }
  }

}
