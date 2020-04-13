package com.fave.breezil.fave.ui.main.sources


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.fave.breezil.fave.R
import com.fave.breezil.fave.callbacks.ArticleClickListener
import com.fave.breezil.fave.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.callbacks.FragmentClosedListener
import com.fave.breezil.fave.databinding.FragmentSourceDetailBinding
import com.fave.breezil.fave.model.Articles
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.ui.adapter.ArticleRecyclerViewAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.main.MainActivity
import com.fave.breezil.fave.ui.main.my_feeds.PreferredViewModel
import com.fave.breezil.fave.utils.Constant.Companion.SOURCENAME
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
class SourceDetailFragment : Fragment() {

    lateinit var binding: FragmentSourceDetailBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private  var articleAdapter: ArticleRecyclerViewAdapter? = null
    lateinit var viewModel: PreferredViewModel


    private var fragmentClosedListener: FragmentClosedListener? = null

    private val sources: Sources?
        get() = if (arguments!!.getParcelable<Sources>(SOURCENAME) != null) {
            arguments!!.getParcelable(SOURCENAME)
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_source_detail, container, false)
        binding.sourceArticleList.setHasFixedSize(true)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(PreferredViewModel::class.java)

        setHasOptionsMenu(true)
        setUpAdapter()
        setUpViewModel(sources!!.id)

        binding.swipeRefresh.setOnRefreshListener { setUpViewModel(sources!!.id) }


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        fragmentClosedListener!!.closeFragment()
    }

    private fun setUpAdapter(){
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
        binding.sourceArticleList.adapter = articleAdapter
    }

    private fun setUpViewModel(source:String){
        binding.swipeRefresh.visibility = View.VISIBLE
        binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorblue, R.color.hotPink)

        viewModel.setParameter(getString(R.string.blank), source, "", todayDate, twoDaysAgoDate, getString(R.string.blank))
        viewModel.articlesList.observe(viewLifecycleOwner, Observer {
            articleAdapter!!.submitList(it)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.second_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object{
        fun getSource(sources: Sources) : SourceDetailFragment {
            val fragment = SourceDetailFragment()
            val args = Bundle()
            args.putParcelable(SOURCENAME, sources)
            fragment.arguments = args
            return fragment
        }
    }


}
