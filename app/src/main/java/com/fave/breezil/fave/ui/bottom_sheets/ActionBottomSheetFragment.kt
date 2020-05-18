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
import com.fave.breezil.fave.databinding.FragmentActionBottomSheetBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.ui.main.WebActivity
import com.fave.breezil.fave.ui.main.bookmark.BookMarkViewModel
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_TITLE
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_URL
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
class ActionBottomSheetFragment : BottomSheetDialogFragment() {

  lateinit var binding: FragmentActionBottomSheetBinding
  private var mContext: Context? = null

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val article: Article?
    get() = if (arguments!!.getParcelable<Article>(ARTICLE) != null) {
      arguments!!.getParcelable(ARTICLE)
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
      DataBindingUtil.inflate(inflater, R.layout.fragment_action_bottom_sheet, container, false)
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
    binding.bookMarkArticle.setOnClickListener {

      startbookMark(article)
    }
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

  private fun startbookMark(article: Article) {
    if (activity != null && isAdded) {

      val title = article.title
      val description = article.description
      val url = article.url
      val urlToImage = article.urlToImage
      val publishedAt = article.publishedAt
      val source = article.source!!.name

      val bookMarkViewModel = ViewModelProvider(this, viewModelFactory)
        .get(BookMarkViewModel::class.java)
      if (title != null && description != null && url != null && urlToImage != null &&
        publishedAt != null
      ) {
        bookMarkViewModel.insert(article).observe(viewLifecycleOwner, Observer {
          if (it == "Successful") {
            Toast.makeText(
              this@ActionBottomSheetFragment.mContext,
              R.string.saved_bookmark,
              Toast.LENGTH_SHORT
            ).show()
          } else {
            Toast.makeText(
              this@ActionBottomSheetFragment.mContext,
              it,
              Toast.LENGTH_SHORT
            ).show()
          }
        })
      } else {
        Toast.makeText(
          this@ActionBottomSheetFragment.mContext,
          getString(R.string.ops_cant_save_bookmark_at_this_time),
          Toast.LENGTH_SHORT
        ).show()
      }
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
    fun getArticles(article: Article): ActionBottomSheetFragment {
      val fragment = ActionBottomSheetFragment()
      val args = Bundle()
      args.putParcelable(ARTICLE, article)
      fragment.arguments = args
      return fragment
    }
  }
} // Required empty public constructor
