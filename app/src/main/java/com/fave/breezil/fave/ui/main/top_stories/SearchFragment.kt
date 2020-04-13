package com.fave.breezil.fave.ui.main.top_stories


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.fave.breezil.fave.R
import com.fave.breezil.fave.callbacks.ArticleClickListener
import com.fave.breezil.fave.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.callbacks.FragmentClosedListener
import com.fave.breezil.fave.databinding.FragmentSearchBinding
import com.fave.breezil.fave.model.Articles
import com.fave.breezil.fave.ui.adapter.ArticleRecyclerViewAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.main.MainActivity
import com.fave.breezil.fave.ui.main.my_feeds.PreferredViewModel
import com.fave.breezil.fave.utils.Constant
import com.fave.breezil.fave.utils.Constant.Companion.sourcesPreferenceList
import com.fave.breezil.fave.utils.Constant.Companion.todayDate
import com.fave.breezil.fave.utils.Constant.Companion.twoDaysAgoDate
import dagger.android.support.AndroidSupportInjection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SearchFragment : Fragment() {

    lateinit var binding : FragmentSearchBinding

    internal var adapter: ArticleRecyclerViewAdapter? = null
    lateinit var viewModel: PreferredViewModel


    private var sortBy: String? = null
    private var source: String = ""

    lateinit var sharedPreferences: SharedPreferences

    private var fragmentClosedListener: FragmentClosedListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(PreferredViewModel::class.java)
        sortBy = sharedPreferences.getString(getString(R.string.pref_everything_sort_by_key), getString(R.string.blank))
        binding.shimmerViewContainer.startShimmer()

        setUpAdapter()
        setUpViewModel()
        search()

        binding.swipeRefresh.setOnRefreshListener { setUpViewModel() }


        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        try {
            this.fragmentClosedListener = context as FragmentClosedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement FragmentClosedListener")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        fragmentClosedListener!!.closeFragment()
    }

    private fun search(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    refresh(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    refresh(newText)
                }
                return true
            }
        })
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

        adapter = ArticleRecyclerViewAdapter(context!!, articleClickListener, articleLongClickListener)


    }

    private fun setUpViewModel(){
        binding.swipeRefresh.visibility = View.VISIBLE
        binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorblue, R.color.hotPink)


        if (!isAdded){
            return
        }
        viewModel.setParameter(getString(R.string.blank), sourcesPreferenceList(context!!,sharedPreferences),sortBy!!, todayDate, twoDaysAgoDate, getString(R.string.blank))
        viewModel.refreshArticle().observe(viewLifecycleOwner, Observer {
            if(it != null){
                adapter!!.submitList(it)
                binding.articleSearchList.adapter = adapter
                adapter!!.notifyDataSetChanged()
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE
            }

        })
        viewModel.getNetworkState().observe(viewLifecycleOwner, Observer {
            if(it != null){
                adapter!!.setNetworkState(it)
            }
        })

        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = preferences.edit()
        editor.putString(getString(R.string.source), source)
        editor.apply()

        if (binding.swipeRefresh != null) {
            binding.swipeRefresh.isRefreshing = false
        }
    }
    private fun refresh(search:String) {
        viewModel.setParameter(search,sourcesPreferenceList(context!!, sharedPreferences), sortBy!!, todayDate, twoDaysAgoDate, getString(R.string.blank))
        viewModel.refreshArticle().observe(viewLifecycleOwner, Observer {
            if(it != null){
                adapter!!.submitList(it)
                binding.articleSearchList.adapter = adapter
                adapter!!.notifyDataSetChanged()
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE
            }else{
                binding.shimmerViewContainer.startShimmer()
                binding.shimmerViewContainer.visibility = View.VISIBLE
            }
        })
        viewModel.getNetworkState().observe(viewLifecycleOwner, Observer {
            if(it != null){
                adapter!!.setNetworkState(it)
            }
        })

    }



}


