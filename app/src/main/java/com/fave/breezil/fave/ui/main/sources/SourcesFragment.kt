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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fave.breezil.fave.R
import com.fave.breezil.fave.ui.callbacks.SeeMoreClickListener
import com.fave.breezil.fave.ui.callbacks.SourcesClickListener
import com.fave.breezil.fave.databinding.FragmentSourcesBinding
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.ui.adapter.QuickCategoryRecyclerAdapter
import com.fave.breezil.fave.ui.adapter.SourceRecyclerAdapter
import com.fave.breezil.fave.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 *
 */
@AndroidEntryPoint
class SourcesFragment : Fragment() {

  lateinit var binding: FragmentSourcesBinding

  private val viewModel: SourcesViewModel by viewModels()

  lateinit var quickCategoryList: List<String>
  private var country: String? = null
  private var language: String? = null

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

    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    country = Constant.getCountry(requireContext(), sharedPreferences)
    language = Constant.getLanguage(requireContext(), sharedPreferences)
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
        requireActivity().supportFragmentManager.beginTransaction()
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
    quickCategoryRecyclerAdapter!!.setList(quickCategoryList)
    binding.sourcesList.adapter = sourceRecyclerAdapter
  }

  private fun setUpViewModel() {
    if (!isAdded) {
      return
    }
    viewModel.getSourcesList("", language!!, country!!).observe(viewLifecycleOwner, Observer {
      it?.let {
        sourceRecyclerAdapter!!.submitList(it)
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
    viewModel.getSourcesList(source, language!!, country!!)
      .observe(viewLifecycleOwner, Observer {
        it?.let { sourceRecyclerAdapter!!.submitList(it) }
      })
  }

}
