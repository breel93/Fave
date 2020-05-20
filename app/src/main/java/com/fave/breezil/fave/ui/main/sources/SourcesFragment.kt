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
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fave.breezil.fave.R
import com.fave.breezil.fave.ui.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.ui.callbacks.SourcesClickListener
import com.fave.breezil.fave.databinding.FragmentSourcesBinding
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.ui.adapter.QuickCategoryRecyclerAdapter
import com.fave.breezil.fave.ui.adapter.SourceRecyclerAdapter
import dagger.android.support.DaggerFragment
import java.util.Collections
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class SourcesFragment : DaggerFragment() {

  lateinit var binding: FragmentSourcesBinding

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  lateinit var viewModel: SourcesViewModel

  lateinit var quickCategoryList: List<String>

  private var sourceRecyclerAdapter: SourceRecyclerAdapter? = null
  private var quickCategoryRecyclerAdapter: QuickCategoryRecyclerAdapter? = null

  lateinit var sharedPreferences: SharedPreferences

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sources, container, false)
    binding.sourcesList.setHasFixedSize(true)

    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    viewModel = ViewModelProvider(this, viewModelFactory).get(SourcesViewModel::class.java)

    binding.shimmerViewContainer.startShimmer()

    setUpAdapter()
    setUpViewModel()

    return binding.root
  }

  private fun setUpAdapter() {
    val seeMoreClickListener = object : SeeMoreClickListener {
      override fun showMoreCategory(category: String) {
        viewSource(category)
      }
    }
    val sourcesClickListener = object : SourcesClickListener {
      override fun showDetails(sources: Sources) {
        val fragment = SourceDetailFragment.getSource(sources)
        fragmentManager!!.beginTransaction()
          .setCustomAnimations(
            R.anim.fragment_slide_in,
            R.anim.fragment_slide_out,
            R.anim.fragment_pop_slide_in,
            R.anim.fragment_pop_slide_out
          )
          .add(R.id.parent_container, fragment)
          .hide(SourcesFragment())
          .addToBackStack("fragment")
          .commit()
      }
    }
    sourceRecyclerAdapter = SourceRecyclerAdapter(sourcesClickListener)

    val textArray = resources.getStringArray(R.array.category_list)
    quickCategoryList = listOf(*textArray)

    val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    quickCategoryRecyclerAdapter =
      QuickCategoryRecyclerAdapter(quickCategoryList, seeMoreClickListener)
    binding.quickChooseList.layoutManager = layoutManager
    binding.quickChooseList.adapter = quickCategoryRecyclerAdapter
    Collections.shuffle(quickCategoryList)
    quickCategoryRecyclerAdapter!!.setList(quickCategoryList)
  }

  private fun setUpViewModel() {
    if (!isAdded) {
      return
    }
    viewModel.getSourcesList("", "", "").observe(viewLifecycleOwner, Observer {
      it?.let {
        sourceRecyclerAdapter!!.submitList(it)
        binding.sourcesList.adapter = sourceRecyclerAdapter
        sourceRecyclerAdapter!!.notifyDataSetChanged()
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.GONE
      }
    })
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (!isAdded) {
      return
    }
  }

  fun viewSource(source: String) {
    viewModel.getSourcesList(source, "", "")
      .observe(viewLifecycleOwner, Observer {
      it?.let { sourceRecyclerAdapter!!.submitList(it) }
    })
  }

}
