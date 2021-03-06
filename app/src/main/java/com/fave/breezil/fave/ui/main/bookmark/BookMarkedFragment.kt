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
package com.fave.breezil.fave.ui.main.bookmark

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentBookmarkedBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.ui.adapter.BookMarkRecyclerAdapter
import com.fave.breezil.fave.ui.bottom_sheets.ActionBottomSheetFragment
import com.fave.breezil.fave.ui.bottom_sheets.DescriptionBottomSheetFragment
import com.fave.breezil.fave.ui.callbacks.ArticleClickListener
import com.fave.breezil.fave.ui.callbacks.ArticleLongClickListener
import com.fave.breezil.fave.utils.Constant.Companion.BOOKMARK_TYPE
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 *
 */

@AndroidEntryPoint
class BookMarkedFragment : Fragment() {

  lateinit var adapter: BookMarkRecyclerAdapter
  private val bookMarkViewModel: BookMarkViewModel by viewModels()
  lateinit var binding: FragmentBookmarkedBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarked, container, false)
    binding.bookmarkList.setHasFixedSize(true)

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    setUpAdapter()
    setUpViewModel()
  }

  private fun setUpAdapter() {
    val bookMarkClickListener = object : ArticleClickListener {
      override fun showDetails(article: Article) {
        val bookMarkDescriptionFragment = DescriptionBottomSheetFragment.getArticles(article)
        bookMarkDescriptionFragment.show(childFragmentManager, getString(R.string.show))
      }
    }

    val bookMarkLongClickListener = object : ArticleLongClickListener {
      override fun doSomething(article: Article) {
        val bookmarkBottomSheetFragment = ActionBottomSheetFragment.getArticles(article,BOOKMARK_TYPE)
        bookmarkBottomSheetFragment.show(childFragmentManager, getString(R.string.show))
      }
    }

    adapter = BookMarkRecyclerAdapter(requireContext(), bookMarkClickListener, bookMarkLongClickListener)
    binding.bookmarkList.adapter = adapter
  }

  private fun setUpViewModel() {
    bookMarkViewModel.bookmarkList.observe(viewLifecycleOwner, Observer { article ->
      adapter.submitList(article)
    })
  }


  companion object {
    fun newInstance() = BookMarkedFragment()
  }
}
