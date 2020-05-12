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
package com.fave.breezil.fave.ui.preference

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.ActivityAboutBinding
import com.fave.breezil.fave.ui.BaseActivity
import com.fave.breezil.fave.utils.Constant.Companion.ABOUT
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.Calendar

class AboutActivity : BaseActivity() {

  lateinit var binding: ActivityAboutBinding
  var toolbar: Toolbar? = null

  private val copyRights: Element
    get() {
      val copyRightsElement = Element()
      val copyrights =
        String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR))
      copyRightsElement.title = copyrights
      copyRightsElement.iconDrawable = R.drawable.ic_copyright_black_24dp
      copyRightsElement.iconTint = mehdi.sakout.aboutpage.R.color.about_item_icon_color
      copyRightsElement.iconNightTint = android.R.color.white
      copyRightsElement.gravity = Gravity.CENTER
      return copyRightsElement
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_about)

    toolbar = binding.root.findViewById(R.id.mainToolbar) as Toolbar
    setSupportActionBar(toolbar)
    supportActionBar!!.title = ABOUT
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    val aboutPage = createPage()
    binding.aboutLayout.addView(aboutPage, 0)
  }

  private fun createPage(): View {
    return AboutPage(this)
      .isRTL(false)
      .setDescription(getString(R.string.about_description))
      .setImage(R.mipmap.ic_launcher_round)
      .addItem(Element().setTitle(String.format(getString(R.string.version))))
      .addGroup(getString(R.string.contacts))
      .addEmail(getString(R.string.email), getString(R.string.email_title))
      .addWebsite(getString(R.string.web), getString(R.string.website))
      .addTwitter(getString(R.string.twitter), getString(R.string.ontwitter))
      .addGitHub(getString(R.string.github), getString(R.string.ongithub))
      .addItem(copyRights)
      .create()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      onBackPressed()
      return true
    }
    return false
  }
}
