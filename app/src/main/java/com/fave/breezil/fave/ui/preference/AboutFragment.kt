package com.fave.breezil.fave.ui.preference

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentAboutBinding
import com.mikepenz.aboutlibraries.Libs.ActivityStyle
import com.mikepenz.aboutlibraries.LibsBuilder
import dagger.hilt.android.AndroidEntryPoint
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.Calendar


@AndroidEntryPoint
class AboutFragment : Fragment() {

  lateinit var binding: FragmentAboutBinding

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
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
    val aboutPage = createPage()
    binding.aboutLayout.addView(aboutPage, 0)

    goBack()
    return binding.root
  }

  private fun createPage(): View {
    return AboutPage(context)
      .isRTL(false)
      .setDescription(getString(R.string.about_description))
      .setImage(R.mipmap.ic_launcher_round)
      .addItem(Element().setTitle(String.format(getString(R.string.version))))
      .addGroup(getString(R.string.contacts))
      .addEmail(getString(R.string.email), getString(R.string.email_title))
      .addWebsite(getString(R.string.web), getString(R.string.website))
      .addTwitter(getString(R.string.twitter), getString(R.string.ontwitter))
      .addGitHub(getString(R.string.github), getString(R.string.ongithub))
      .addItem(getLibElement())
      .addItem(copyRights)
      .create()
  }

  private fun getLibElement(): Element? {
    val libElement = Element()
    libElement.title = getString(R.string.open_source_libs)
    libElement.onClickListener = View.OnClickListener {
      LibsBuilder()
        .withActivityStyle(ActivityStyle.LIGHT_DARK_TOOLBAR)
        .withActivityTitle(getString(R.string.libs_text))
        .withAutoDetect(true)
        .start(requireContext())
    }
    return libElement
  }
  private fun goBack(){
    binding.backPressed.setOnClickListener{
      requireActivity().supportFragmentManager.popBackStack()
    }
  }
}