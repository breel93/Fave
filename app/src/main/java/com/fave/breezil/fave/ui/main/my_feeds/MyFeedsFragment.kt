package com.fave.breezil.fave.ui.main.my_feeds


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.fave.breezil.fave.R
import com.fave.breezil.fave.callbacks.ArticleClickListener
import com.fave.breezil.fave.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.databinding.FragmentMyFeedsBinding
import com.fave.breezil.fave.model.Articles
import com.fave.breezil.fave.ui.adapter.ArticleRecyclerViewAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.utils.Constant.Companion.sourcesPreferenceList
import com.fave.breezil.fave.utils.Constant.Companion.todayDate
import com.fave.breezil.fave.utils.Constant.Companion.twoDaysAgoDate
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MyFeedsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: FragmentMyFeedsBinding
    private var articleAdapter: ArticleRecyclerViewAdapter? = null
    lateinit var viewModel: PreferredViewModel

    lateinit var sharedPreferences: SharedPreferences


    private var sortBy: String? = null
    private var source: String = ""


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_feeds, container, false)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        binding.feedList.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PreferredViewModel::class.java)

        sortBy = sharedPreferences.getString(getString(R.string.pref_everything_sort_by_key), getString(R.string.blank))
        binding.shimmerViewContainer.startShimmer()
        binding.swipeRefresh.setOnRefreshListener { refresh() }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpAdapter()
        setUpViewModel()
    }


    private fun setUpAdapter() {
        val articleClickListener = object : ArticleClickListener {
            override fun showDetails(article: Articles) {
                val descriptionBottomSheetFragment = DescriptionBottomSheetFragment.getArticles(article)
                descriptionBottomSheetFragment.show(fragmentManager!!, getString(R.string.show))
            }
        }
        val articleLongClickListener = object : ArticleLongClickListener {
            override fun doSomething(article: Articles) {
                val actionBottomSheetFragment = ActionBottomSheetFragment.getArticles(article)
                actionBottomSheetFragment.show(fragmentManager!!, getString(R.string.show))
            }
        }
        articleAdapter = ArticleRecyclerViewAdapter(context!!, articleClickListener, articleLongClickListener)

    }

    private fun setUpViewModel() {
        if (!isAdded) {
            return
        }

        binding.swipeRefresh.visibility = View.VISIBLE
        binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorblue, R.color.hotPink)


        viewModel.setParameter(getString(R.string.blank), sourcesPreferenceList(context!!, sharedPreferences), sortBy!!, todayDate, twoDaysAgoDate, getString(R.string.blank))
        viewModel.articlesList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                articleAdapter!!.submitList(it)
                binding.feedList.adapter = articleAdapter
                articleAdapter!!.notifyDataSetChanged()
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE
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

        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = preferences.edit()
        editor.putString(getString(R.string.source), source)
        editor.apply()
    }

    private fun refresh() {
        binding.swipeRefresh.visibility = View.VISIBLE
        binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorblue, R.color.hotPink)

        viewModel.setParameter(getString(R.string.blank), sourcesPreferenceList(context!!, sharedPreferences), sortBy!!, todayDate, twoDaysAgoDate, getString(R.string.blank))
        viewModel.refreshArticle().observe(viewLifecycleOwner, Observer
        { articles -> articleAdapter!!.submitList(articles) })

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
        fun newInstance() = MyFeedsFragment()
    }

}
