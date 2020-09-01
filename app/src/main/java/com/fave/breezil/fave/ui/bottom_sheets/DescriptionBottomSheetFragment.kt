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

import android.content.res.Resources
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentDescriptionBottomSheetBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE
import com.fave.breezil.fave.utils.getTimeAgo
import com.fave.breezil.fave.utils.helpers.HtmlTagHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 */
class DescriptionBottomSheetFragment : BottomSheetDialogFragment() {

  lateinit var binding: FragmentDescriptionBottomSheetBinding
  private lateinit var circularProgressDrawable: CircularProgressDrawable

  private val article: Article?
    get() = if (requireArguments().getParcelable<Article>(ARTICLE) != null) {
      requireArguments().getParcelable(ARTICLE)
    } else {
      null
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_description_bottom_sheet,
      container,
      false
    )
    updateUi(article!!)
    return binding.root
  }

  private fun updateUi(article: Article) {
    circularProgressDrawable = CircularProgressDrawable(requireContext())
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 40f
    circularProgressDrawable.setColorSchemeColors(
      R.color.colorAccent, R.color.colorPrimary,
      R.color.colorblue, R.color.hotPink
    )
    circularProgressDrawable.start()
    Glide.with(requireContext())
      .load(article.urlToImage)
      .apply(
        RequestOptions()

          .placeholder(circularProgressDrawable)
          .error(R.drawable.placeholder)
      )
      .into(binding.articleImage)
    binding.articleDescriptions.text = article.description
    binding.articleTitle.text = Html.fromHtml (article.title, null, HtmlTagHandler())
    binding.articleSource.text = article.source!!.name
    binding.publishedAt.text = article.publishedAt!!.asTimeAgo(requireContext().resources) + " |"
  }

  private fun Date.asTimeAgo(resources: Resources): String {
    return getTimeAgo(this.time, System.currentTimeMillis(), resources)
  }

  companion object {
    fun getArticles(article: Article): DescriptionBottomSheetFragment {
      val fragment = DescriptionBottomSheetFragment()
      val args = Bundle()
      args.putParcelable(ARTICLE, article)
      fragment.arguments = args
      return fragment
    }
  }
} // Required empty public constructor
