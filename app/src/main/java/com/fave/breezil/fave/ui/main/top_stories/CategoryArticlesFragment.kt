package com.fave.breezil.fave.ui.main.top_stories


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager

import com.fave.breezil.fave.R
import com.fave.breezil.fave.callbacks.ArticleClickListener
import com.fave.breezil.fave.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.callbacks.FragmentClosedListener
import com.fave.breezil.fave.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.databinding.FragmentCategoryArticlesBinding
import com.fave.breezil.fave.model.Articles
import com.fave.breezil.fave.ui.adapter.ArticleRecyclerViewAdapter
import com.fave.breezil.fave.ui.adapter.QuickCategoryRecyclerAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.main.MainActivity
import com.fave.breezil.fave.utils.Constant.Companion.CATEGORYNAME
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject







/**
 * A simple [Fragment] subclass.
 *
 */

class CategoryArticlesFragment : Fragment() {

    lateinit var binding: FragmentCategoryArticlesBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private  var articleAdapter: ArticleRecyclerViewAdapter? = null
    private  var quickCategoryRecyclerAdapter : QuickCategoryRecyclerAdapter? = null
    lateinit var quickCategoryList: List<String>


    lateinit var viewModel: MainViewModel

    private var country: String? = null

    lateinit var sharedPreferences: SharedPreferences
    


    private var fragmentClosedListener: FragmentClosedListener? = null

    private val category: String?
        get() = if (arguments!!.getString(CATEGORYNAME) != null) {
            arguments!!.getString(CATEGORYNAME)
        } else {
            null
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

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_articles, container, false)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainViewModel::class.java)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        country = sharedPreferences.getString(getString(R.string.country_key), getString(R.string.blank))

        setUpAdapter()
        setUpViewModel(category!!)
        
        binding.shimmerViewContainer.startShimmer()
        binding.swipeRefresh.setOnRefreshListener { refresh(category!!) }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentClosedListener!!.closeFragment()
    }


    private fun setUpAdapter() {

        val seeMoreClickListener  = object : SeeMoreClickListener {
            override fun showMoreCategory(category: String) {
                refresh(category)
            }

        }
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



        val textArray = resources.getStringArray(R.array.category_list)
        quickCategoryList = Arrays.asList(*textArray)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        quickCategoryRecyclerAdapter = QuickCategoryRecyclerAdapter(quickCategoryList,seeMoreClickListener )
        binding.quickChooseList.layoutManager = layoutManager
        binding.quickChooseList.adapter = quickCategoryRecyclerAdapter
        Collections.shuffle(quickCategoryList)
        quickCategoryRecyclerAdapter!!.setList(quickCategoryList)

    }

    private fun setUpViewModel(category:String){
        binding.swipeRefresh.visibility = View.VISIBLE
        binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorblue, R.color.hotPink)


        viewModel.articlesList.removeObservers(viewLifecycleOwner)

        viewModel.setParameter(country!!,getString(R.string.blank),category,getString(R.string.blank))
        viewModel.articlesList.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                articleAdapter!!.submitList(it)
                binding.articleCategoryList.adapter = articleAdapter
                articleAdapter!!.notifyDataSetChanged()
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE
            }
        })
        viewModel.getNetworkState().observe(viewLifecycleOwner, Observer{ networkState ->
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.second_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }



    private fun refresh(category: String){
        binding.swipeRefresh.visibility = View.VISIBLE
        binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorblue, R.color.hotPink)

        (activity as AppCompatActivity).supportActionBar!!.title = category
        viewModel.setParameter( country!!,getString(R.string.blank),category,getString(R.string.blank))
        viewModel.refreshArticle().observe(viewLifecycleOwner, Observer {
            if(it != null) {
                articleAdapter!!.submitList(it)
                binding.articleCategoryList.adapter = articleAdapter
                articleAdapter!!.notifyDataSetChanged()
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE
            }else{
                binding.shimmerViewContainer.startShimmer()
                binding.shimmerViewContainer.visibility = View.VISIBLE
            }

        })
        viewModel.getNetworkState().observe(viewLifecycleOwner, Observer{ networkState ->
            if (networkState != null) {
                articleAdapter!!.setNetworkState(networkState)
            }
        })


        if (binding.swipeRefresh != null) {
            binding.swipeRefresh.isRefreshing = false
        }
    }


}
