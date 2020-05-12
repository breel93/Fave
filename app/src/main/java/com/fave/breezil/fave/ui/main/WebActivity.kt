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
package com.fave.breezil.fave.ui.main

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.ActivityWebBinding
import com.fave.breezil.fave.ui.BaseActivity
import com.fave.breezil.fave.ui.preference.SettingsActivity
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_TITLE
import com.fave.breezil.fave.utils.Constant.Companion.ARTICLE_URL
import com.fave.breezil.fave.utils.helpers.HtmlTagHandler

class WebActivity : BaseActivity() {

  lateinit var binding: ActivityWebBinding

  var toolbar: Toolbar? = null

  private fun getArticleTitle(): String? {
    return if (intent.hasExtra(ARTICLE_TITLE)) {
      intent.getStringExtra(ARTICLE_TITLE)
    } else {
      null
    }
  }

  private fun getArticleUrl(): String? {
    return if (intent.hasExtra(ARTICLE_URL)) {
      intent.getStringExtra(ARTICLE_URL)
    } else {
      null
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_web)

    toolbar = binding.root.findViewById(R.id.mainToolbar) as Toolbar
    setSupportActionBar(toolbar)
    supportActionBar!!.title = Html.fromHtml(getArticleTitle(), null, HtmlTagHandler())
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    loadWeb(getArticleUrl()!!)
  }

  private fun loadWeb(url: String) {
    binding.webView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
        view.loadUrl(url)
        return true
      }

      override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        binding.webLoadingProgress.visibility = View.VISIBLE
      }

      override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        binding.webLoadingProgress.visibility = View.GONE
      }

      override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError?
      ) {
        super.onReceivedError(view, request, error)
        binding.webLoadingProgress.visibility = View.GONE
      }

//            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
//                super.onReceivedSslError(view, handler, error)
//                handler!!.proceed()
//            }
    }
    binding.webView.loadUrl(url)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    super.onCreateOptionsMenu(menu)
    // set the menu layout
    menuInflater.inflate(R.menu.webmenu, menu)
    return true
  }

  // Option menu selected
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    super.onOptionsItemSelected(item)
    if (item.itemId == R.id.preference) {
      val intent = Intent(this@WebActivity, SettingsActivity::class.java)
      startActivity(intent)
    }
    if (item.itemId == android.R.id.home) {
      onBackPressed()
      return true
    }

    if (item.itemId == R.id.refresh) {
      loadWeb(getArticleUrl()!!)
    }
    if (item.itemId == R.id.share) {
      share()
    }
    if (item.itemId == R.id.browser) {
      val i = Intent(Intent.ACTION_VIEW)
      i.data = Uri.parse(getArticleUrl())
      startActivity(i)
    }

    return true
  }

  private fun share() {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = getString(R.string.text_slash_plain)
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getArticleTitle())
    shareIntent.putExtra(Intent.EXTRA_TEXT, getArticleUrl())
    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_link)))
  }
}
