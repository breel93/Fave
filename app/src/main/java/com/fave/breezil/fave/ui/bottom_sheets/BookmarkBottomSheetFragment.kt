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
package com.fave.breezil.fave.ui.bottom_sheets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentBookmarkBottomSheetBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.ui.main.WebActivity
import com.fave.breezil.fave.ui.main.bookmark.BookMarkViewModel
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_TITLE
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_URL
import com.fave.breezil.fave.utils.Constant.Companion.BOOKMARK
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 */
class BookmarkBottomSheetFragment : BottomSheetDialogFragment() {

  private var mContext: Context? = null
  lateinit var bookMarkViewModel: BookMarkViewModel
  lateinit var binding: FragmentBookmarkBottomSheetBinding
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val article: Article?
    get() = if (arguments!!.getParcelable<Article>(BOOKMARK) != null) {
      arguments!!.getParcelable(BOOKMARK)
    } else {
      null
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark_bottom_sheet, container, false)
    bookMarkViewModel = ViewModelProvider(this, viewModelFactory).get(BookMarkViewModel::class.java)
    this.mContext = activity
    updateUi(this.article!!)
    return binding.root
  }
  override fun onAttach(context: Context) {
    super.onAttach(context)
    AndroidSupportInjection.inject(this)
  }
  private fun updateUi(article: Article) {
    binding.fullArticle.setOnClickListener { startWeb(article) }
    binding.shareArticle.setOnClickListener { startSharing(article) }
    binding.deleteBookmark.setOnClickListener { showDeleteDialog(article) }
  }

  private fun showDeleteDialog(article: Article) {
//        val builder = AlertDialog.Builder(context,R.style.MyDialogTheme)
//        builder.setCancelable(false)
//        builder.setMessage(R.string.Are_you_sure_you_want_to_delete_this_bookmark).setPositiveButton(R.string.yes) { _, _ ->
//            bookMarkViewModel.delete(bookMark)
//
//            Toast.makeText(mContext,
//                    resources.getString(R.string.bookmark_deleted), Toast.LENGTH_SHORT).show()
//
//            dismiss()
//        }
//                .setNegativeButton(R.string.no) { dialog, _ -> dialog.dismiss() }
//        val alertDialog = builder.create()
//        alertDialog.setTitle(getString(R.string.delete_bookmark))
//        alertDialog.show()
    bookMarkViewModel.delete(article).observe(viewLifecycleOwner, Observer {
      if (it == "Successful") {
        Toast.makeText(
          this@BookmarkBottomSheetFragment.mContext,
          R.string.bookmark_deleted,
          Toast.LENGTH_SHORT
        ).show()
      } else {
        Toast.makeText(
          this@BookmarkBottomSheetFragment.mContext,
          it,
          Toast.LENGTH_SHORT
        ).show()
      }
    })
    dismiss()
  }

  private fun startSharing(article: Article) {
    val activity = activity
    if (activity != null && isAdded) {
      val shareIntent = Intent(Intent.ACTION_SEND)
      shareIntent.type = getString(R.string.text_slash_plain)
      shareIntent.putExtra(Intent.EXTRA_SUBJECT, article.title)
      shareIntent.putExtra(Intent.EXTRA_TEXT, article.url)
      startActivity(Intent.createChooser(shareIntent, getString(R.string.share_link)))
    }
    dismiss()
  }

  private fun startWeb(article: Article) {
    val activity = activity
    if (activity != null && isAdded) {
      val webIntent = Intent(context, WebActivity::class.java)
      webIntent.putExtra(ARTICLE_TITLE, article.title)
      webIntent.putExtra(ARTICLE_URL, article.url)
      startActivity(webIntent)
    }
    dismiss()
  }

  companion object {

    fun getBookmark(article: Article): BookmarkBottomSheetFragment {
      val bookmarkBottomSheetFragment = BookmarkBottomSheetFragment()
      val args = Bundle()
      args.putParcelable(BOOKMARK, article)
      bookmarkBottomSheetFragment.arguments = args
      return bookmarkBottomSheetFragment
    }
  }
} // Required empty public constructor
