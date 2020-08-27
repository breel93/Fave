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

import android.content.Context
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
import androidx.recyclerview.widget.GridLayoutManager
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentSourceDetailBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.repository.NetworkState
import com.fave.breezil.fave.ui.adapter.ArticleRecyclerViewAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.ui.callbacks.FragmentOpenedListener
import com.fave.breezil.fave.ui.main.look_up.LookUpViewModel
import com.fave.breezil.fave.utils.Constant
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_TYPE
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
  private lateinit var openedListener: FragmentOpenedListener
  lateinit var sharedPreferences: SharedPreferences
  private var language: String? = null

  private val sources: Sources?
    get() = if (requireArguments().getParcelable<Sources>(SOURCENAME) != null) {
      requireArguments().getParcelable(SOURCENAME)
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
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    language = Constant.getLanguage(requireContext(), sharedPreferences)
    viewModel = ViewModelProvider(this, viewModelFactory).get(LookUpViewModel::class.java)
    binding.shimmerViewContainer.startShimmer()
    setHasOptionsMenu(true)
    setupLoading()
    setUpAdapter()
    setUpViewModel(sources!!)
    goBack()

    openedListener.isOpened(true)
    binding.swipeRefresh.setOnRefreshListener { setUpViewModel(sources!!) }
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
    binding.sourceArticleList.layoutManager = layoutGridManager
    binding.sourceArticleList.adapter = articleAdapter
  }

  private fun setUpViewModel(source: Sources) {
    binding.sourceText.text = source.name
    binding.swipeRefresh.visibility = View.VISIBLE
    binding.swipeRefresh.setColorSchemeResources(
      R.color.colorAccent,  R.color.hotPink
    )
    viewModel.setParameter(
      getString(R.string.blank), source.id, "", todayDate, twoDaysAgoDate, language = language!!)
    viewModel.articleList.observe(viewLifecycleOwner, Observer {
      binding.shimmerViewContainer.visibility = View.GONE
      it?.let { articleAdapter!!.submitList(it) }
      if(it.size > 0){
        articleAdapter!!.setFirstArticle(it[1]!!)
      }
    })
    binding.swipeRefresh.let { binding.swipeRefresh.isRefreshing = false }
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
            binding.sourceArticleList.visibility = View.VISIBLE
          }
          NetworkState.Status.FAILED -> {
            binding.searchError.visibility= View.GONE
            binding.responseError.visibility = View.VISIBLE
            binding.sourceArticleList.visibility = View.GONE
          }
          NetworkState.Status.NO_RESULT -> {
//            binding.searchError.visibility = View.VISIBLE
//            binding.responseError.visibility = View.GONE
//            binding.sourceArticleList.visibility = View.GONE
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


  companion object {
    fun getSource(sources: Sources): SourceDetailFragment {
      val fragment = SourceDetailFragment()
      val args = Bundle()
      args.putParcelable(SOURCENAME, sources)
      fragment.arguments = args
      return fragment
    }
  }

  private fun goBack(){
    binding.backPressed.setOnClickListener{
      requireActivity().supportFragmentManager.popBackStack();
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    openedListener.isOpened(false)
  }
}
