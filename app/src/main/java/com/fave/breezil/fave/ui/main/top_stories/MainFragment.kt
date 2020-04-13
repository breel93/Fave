package com.fave.breezil.fave.ui.main.top_stories


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

import com.fave.breezil.fave.R
import com.fave.breezil.fave.callbacks.ArticleClickListener
import com.fave.breezil.fave.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.databinding.FragmentMainBinding
import com.fave.breezil.fave.model.Articles
import com.fave.breezil.fave.model.ParentModel
import com.fave.breezil.fave.ui.adapter.ParentCategoryRecyclerAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.main.MainActivity
import com.fave.breezil.fave.ui.preference.AboutActivity
import com.fave.breezil.fave.ui.preference.SettingsActivity
import com.fave.breezil.fave.utils.Constant
import com.fave.breezil.fave.utils.Constant.Companion.DEFAULT_SOURCE
import com.fave.breezil.fave.utils.Constant.Companion.ONE
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
class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding : FragmentMainBinding
    private lateinit var parentCategoryRecyclerAdapter : ParentCategoryRecyclerAdapter
    private var mArrayList = ArrayList<ParentModel>()

    private lateinit var viewModel : MainViewModel

    private var country: String? = null
    private var sortBy: String? = null

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        country = sharedPreferences.getString(getString(R.string.country_key), getString(R.string.blank))
        sortBy = sharedPreferences.getString(getString(R.string.pref_everything_sort_by_key), getString(R.string.blank))

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)


        binding.shimmerViewContainer.startShimmer()

        binding.swipeRefresh.setOnRefreshListener { setupViewModel() }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         setupAdapter()
         setupViewModel()
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun setupAdapter(){
        val articleClickListener = object :ArticleClickListener{
            override fun showDetails(article: Articles) {
                val descriptionBottomSheetFragment = DescriptionBottomSheetFragment.getArticles(article)
                descriptionBottomSheetFragment.show(fragmentManager!!, getString(R.string.show))
            }
        }
        val articleLongClickListener = object : ArticleLongClickListener{
            override fun doSomething(article: Articles) {
                val actionBottomSheetFragment = ActionBottomSheetFragment.getArticles(article)
                actionBottomSheetFragment.show(fragmentManager!!, getString(R.string.show))
            }
        }
        val seeMoreClickListener = object : SeeMoreClickListener{
            override fun showMoreCategory(category: String) {
                (activity as MainActivity).loadCategoryFragment(category)
            }
        }
        parentCategoryRecyclerAdapter = ParentCategoryRecyclerAdapter(context!!,articleClickListener,articleLongClickListener,seeMoreClickListener)
    }

    private fun setupViewModel(){
        binding.swipeRefresh.visibility = View.VISIBLE
        binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorblue, R.color.hotPink)

        viewModel.getCategoryList(context!!,mArrayList,country!!,"","")?.removeObservers(viewLifecycleOwner)

        viewModel.getCategoryList(context!!,mArrayList,country!!,"","")?.observe(viewLifecycleOwner, Observer {
            if (it && ::parentCategoryRecyclerAdapter.isInitialized) {
                binding.mainRecyclerView.adapter = parentCategoryRecyclerAdapter
                parentCategoryRecyclerAdapter.setList(mArrayList)

                parentCategoryRecyclerAdapter.notifyDataSetChanged()
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE
            }
        })

        viewModel.getBreakingNewList(sourcesPreferenceList(context!!,sharedPreferences),sortBy, todayDate, twoDaysAgoDate, getString(R.string.blank)).removeObservers(viewLifecycleOwner)
        viewModel.getBreakingNewList(sourcesPreferenceList(context!!,sharedPreferences),sortBy, todayDate, twoDaysAgoDate, getString(R.string.blank)).observe(viewLifecycleOwner, Observer {
            if(it !=null){
                parentCategoryRecyclerAdapter.setBreakingNews(it as ArrayList<Articles>)
                parentCategoryRecyclerAdapter.notifyDataSetChanged()
            }

        })

        if (binding.swipeRefresh != null) {
            binding.swipeRefresh.isRefreshing = false
        }

    }

    companion object {
        fun newInstance() = MainFragment()
    }



}
