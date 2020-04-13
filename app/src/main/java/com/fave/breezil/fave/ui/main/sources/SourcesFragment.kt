package com.fave.breezil.fave.ui.main.sources


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.fave.breezil.fave.R
import com.fave.breezil.fave.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.callbacks.SourcesClickListener
import com.fave.breezil.fave.databinding.FragmentSourcesBinding
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.ui.adapter.QuickCategoryRecyclerAdapter
import com.fave.breezil.fave.ui.adapter.SourceRecyclerAdapter
import com.fave.breezil.fave.ui.main.MainActivity
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class SourcesFragment : Fragment() {

    lateinit var binding: FragmentSourcesBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SourcesViewModel

    lateinit var quickCategoryList: List<String>

    private var sourceRecyclerAdapter : SourceRecyclerAdapter? = null
    private var quickCategoryRecyclerAdapter : QuickCategoryRecyclerAdapter? = null


    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sources, container, false)
        binding.sourcesList.setHasFixedSize(true)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(SourcesViewModel::class.java)


        binding.shimmerViewContainer.startShimmer()

        setUpAdapter()
        setUpViewModel()

        return binding.root
    }

    private fun setUpAdapter(){

        val seeMoreClickListener  = object : SeeMoreClickListener {
            override fun showMoreCategory(category: String) {
                viewSource(category)
            }
        }

        val sourcesClickListener = object : SourcesClickListener{
            override fun showDetails(sources: Sources) {
                (activity as MainActivity).loadSourcesDetailFragment(sources)
            }
        }

        sourceRecyclerAdapter = SourceRecyclerAdapter(sourcesClickListener)



        val textArray = resources.getStringArray(R.array.category_list)
        quickCategoryList = Arrays.asList(*textArray)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        quickCategoryRecyclerAdapter = QuickCategoryRecyclerAdapter(quickCategoryList,seeMoreClickListener )
        binding.quickChooseList.layoutManager = layoutManager
        binding.quickChooseList.adapter = quickCategoryRecyclerAdapter
        Collections.shuffle(quickCategoryList)
        quickCategoryRecyclerAdapter!!.setList(quickCategoryList)
    }

    private fun setUpViewModel(){
        if(!isAdded){
            return
        }
        viewModel.getSourcesList("","","").observe(viewLifecycleOwner, Observer {
            if(it != null){
                sourceRecyclerAdapter!!.submitList(it)
                binding.sourcesList.adapter = sourceRecyclerAdapter
                sourceRecyclerAdapter!!.notifyDataSetChanged()
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE

            }

        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if(!isAdded){
            return
        }
    }
    fun viewSource(source: String){
        viewModel.getSourcesList(source,"","").observe(viewLifecycleOwner, Observer {
            sourceRecyclerAdapter!!.submitList(it)
        })
    }


    companion object {
        fun newInstance() = SourcesFragment()
    }


}
