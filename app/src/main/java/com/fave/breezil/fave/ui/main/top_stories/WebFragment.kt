package com.fave.breezil.fave.ui.main.top_stories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentWebBinding
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE
import dagger.android.support.DaggerFragment
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [WebFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebFragment : DaggerFragment() {

  lateinit var binding : FragmentWebBinding
  private val article: Article?
    get() = if (arguments!!.getParcelable<Article>(ARTICLE) != null) {
      arguments!!.getParcelable(ARTICLE)
    } else {
      null
    }
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web, container, false)
    showWebView(article!!)
    goBack()
    return binding.root
  }

  private fun showWebView(article: Article){
    binding.articleWebView.apply {
      webViewClient = object : WebViewClient(){
        override fun onPageFinished(view: WebView?, url: String?) {
          binding.articleProgressBar.isGone = true
        }
      }
      loadUrl(article.url)
    }
    binding.articleTitleText.text = article.title
  }

  companion object{
    fun getArticles(article:Article): WebFragment{
      val fragment = WebFragment()
      val args  = Bundle()
      args.putParcelable(ARTICLE, article)
      fragment.arguments = args
      return fragment
    }
  }
  private fun goBack(){
    binding.backPressed.setOnClickListener{
      fragmentManager!!.popBackStack();
    }
  }
}