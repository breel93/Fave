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

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.ActivityMainBinding
import com.fave.breezil.fave.ui.BaseActivity
import com.fave.breezil.fave.ui.adapter.PagerAdapter
import com.fave.breezil.fave.ui.main.bookmark.BookMarkedFragment
import com.fave.breezil.fave.ui.main.sources.SourcesFragment
import com.fave.breezil.fave.ui.main.top_stories.MainFragment
import com.fave.breezil.fave.ui.main.top_stories.SearchFragment
import com.fave.breezil.fave.ui.preference.SettingsFragment
import com.fave.breezil.fave.utils.Constant.Companion.ZERO
import com.fave.breezil.fave.utils.helpers.BottomNavigationHelper
import com.fave.breezil.fave.utils.helpers.FadeOutTransformation
import com.fave.breezil.fave.utils.helpers.NonSwipeableViewPager
import dagger.android.AndroidInjection
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class MainActivity : BaseActivity() {

  lateinit var binding: ActivityMainBinding
  lateinit var sharedPreferences: SharedPreferences

  private var source: String = ""
  private var toolbar : Toolbar? = null

  var mainViewPagerAdapter: PagerAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    mainViewPagerAdapter = PagerAdapter(supportFragmentManager)

    setupBottomNavigation()

    val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
      override fun log(message: String) {
        Timber.tag("OkHttp").d(message)
      }
    })
    logging.redactHeader(getString(R.string.authorization))
    logging.redactHeader(getString(R.string.cookie))

    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = preferences.edit()
    editor.putString(getString(R.string.source), source)
    editor.apply()
    toolbar = binding.root.findViewById(R.id.mainToolbar) as Toolbar
    setSupportActionBar(toolbar)
    updateToolbarTitle(binding.mainViewPager)
  }

  private fun updateToolbarTitle(viewPager: NonSwipeableViewPager){
    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
      override fun onPageScrollStateChanged(state: Int) {
      }

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
      }

      override fun onPageSelected(position: Int) {
        when(position){
          0 -> {
            supportActionBar!!.title = "Top Stories"
            toolbar!!.visibility = View.VISIBLE
          }
          1 ->{
            toolbar!!.visibility = View.GONE
          }
          2->{
            supportActionBar!!.title = "Sources"
            toolbar!!.visibility = View.VISIBLE
          }
          3->{
            supportActionBar!!.title = "BookMarked"
            toolbar!!.visibility = View.VISIBLE
          }
        }
      }

    })
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    super.onCreateOptionsMenu(menu)
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  // Option menu selected
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    super.onOptionsItemSelected(item)
    if (item.itemId == R.id.preference) {
      val fragment = SettingsFragment()
      supportFragmentManager.beginTransaction()
        .setCustomAnimations(
          R.anim.fragment_slide_in,
          R.anim.fragment_slide_out,
          R.anim.fragment_pop_slide_in,
          R.anim.fragment_pop_slide_out
        )
        .add(R.id.parent_container, fragment)
        .addToBackStack("fragment")
        .commit()
    }
    if (item.itemId == R.id.about) {

    }
    return true
  }

  private fun setupBottomNavigation() {

    mainViewPagerAdapter!!.addFragments(MainFragment())
    mainViewPagerAdapter!!.addFragments(SearchFragment())
    mainViewPagerAdapter!!.addFragments(SourcesFragment())
    mainViewPagerAdapter!!.addFragments(BookMarkedFragment())
    binding.mainViewPager.adapter = mainViewPagerAdapter
    binding.mainViewPager.setPageTransformer(false, FadeOutTransformation())
    binding.mainViewPager.offscreenPageLimit = 3

    BottomNavigationHelper.disableShiftMode(binding.bottomNavViewBar)
    val menu = binding.bottomNavViewBar.menu
    val menuItem = menu.getItem(ZERO)
    menuItem.isChecked = true

    binding.bottomNavViewBar.setOnNavigationItemSelectedListener { item ->
      loadFragmentBottomNav(item)
      true
    }
  }

  private fun loadFragmentBottomNav(item: MenuItem) {
    when (item.itemId) {
      R.id.main -> {
        binding.mainViewPager.setCurrentItem(0, true)
      }
      R.id.explore -> {
        binding.mainViewPager.setCurrentItem(1, true)
      }
      R.id.source -> {
        binding.mainViewPager.setCurrentItem(2, true)
      }
      R.id.bookmark -> {
        binding.mainViewPager.setCurrentItem(3, true)
      }
    }
  }

}
