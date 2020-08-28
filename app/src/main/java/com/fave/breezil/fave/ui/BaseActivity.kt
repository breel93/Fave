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
package com.fave.breezil.fave.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.fave.breezil.fave.R
import com.fave.breezil.fave.utils.Constant
import com.fave.breezil.fave.utils.Constant.Companion.DEFAULT_SOURCE
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
  private var sharedPreferences: SharedPreferences? = null

  private var themeMode: Boolean = false

  @SuppressLint("SourceLockedOrientationActivity")
  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    themeMode = sharedPreferences!!.getBoolean(getString(R.string.pref_theme_key), false)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    setAppTheme(themeMode)
  }

  override fun onResume() {
    super.onResume()
    val selectedTheme = sharedPreferences!!.getBoolean(getString(R.string.pref_theme_key), false)
    if (themeMode != selectedTheme)
      recreate()
  }

  private fun setAppTheme(currentTheme: Boolean) {
    when (currentTheme) {
      true -> setTheme(R.style.DarkTheme)
      else -> setTheme(R.style.AppTheme)
    }
  }

  val twoDaysAgoDate: String
    get() {

      val dateFormat = SimpleDateFormat("yyyy-MM-dd")
      val cal = Calendar.getInstance()
      cal.add(Calendar.DATE, -2)
      return dateFormat.format(cal.time)
    }

  val todayDate: String
    get() {

      val dateFormat = SimpleDateFormat("yyyy-MM-dd")
      val cal = Calendar.getInstance()
      return dateFormat.format(cal.time)
    }

  fun sourcesPreferenceList(sharedPreferences: SharedPreferences): String {

    val sourceSet = HashSet<String>()
    sourceSet.add(getString(R.string.pref_sources_all_value))

    val sharedSet = sharedPreferences.getStringSet(getString(R.string.pref_source_key),sourceSet)
    val entries = arrayListOf<String>(sharedSet.toString())
    val selectedSources = StringBuilder()

    for (i in entries.indices) {
      selectedSources.append(entries[i]).append(getString(R.string.comma))
    }

    if (selectedSources.isNotEmpty()) {
      selectedSources.deleteCharAt(selectedSources.length - Constant.ONE)
    }

      return if (selectedSources.isEmpty()) {
        DEFAULT_SOURCE
      } else {
        selectedSources.toString()
      }
  }
}
