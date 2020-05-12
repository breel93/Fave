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

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceGroup
import androidx.preference.Preference
import androidx.preference.SwitchPreference
import androidx.preference.ListPreference
import com.fave.breezil.fave.R
import java.util.Objects
import kotlin.collections.ArrayList

class PrefFragment : PreferenceFragmentCompat(),
  SharedPreferences.OnSharedPreferenceChangeListener {

  private var mSourcePref: MultiSelectListPreference? = null

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.preferences, rootKey)
    PreferenceManager.setDefaultValues(
      Objects.requireNonNull<FragmentActivity>(activity),
      R.xml.preferences,
      false
    )

    val preferenceGroup = preferenceScreen.getPreference(0) as PreferenceGroup

    mSourcePref = preferenceGroup.getPreference(0) as MultiSelectListPreference

    initSummary(preferenceScreen)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val listView = view.findViewById<ListView>(android.R.id.list)
    if (listView != null)
      ViewCompat.setNestedScrollingEnabled(listView, true)
  }

  override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
    if (key != getString(R.string.pref_source_key)) {
//            updateSummary(findPreference(key))
//            updateNightMode(findPreference(key))
      activity!!.recreate()
    } else {
//            updateMultiSummary(findPreference(key),
//                    sharedPreferences.getStringSet(getString(R.string.pref_source_key), null))
    }
  }

  private fun initSummary(p: Preference) {
    if (p is PreferenceGroup) {
      for (i in 0 until p.preferenceCount) {
        initSummary(p.getPreference(i))
      }
    } else {
      updateSummary(p)
      updateMultiSummary(
        p,
        PreferenceManager.getDefaultSharedPreferences(
          Objects.requireNonNull<FragmentActivity>(activity)
        )
          .getStringSet(getString(R.string.pref_source_key), null)
      )
      updateNightMode(p)
    }
  }

  private fun updateSummary(p: Preference) {
    if (p is ListPreference) {
      p.setSummary(p.entry)
    }
  }

  private fun updateMultiSummary(p: Preference, value: Set<String>?) {
    if (p is MultiSelectListPreference) {

      val entries = ArrayList(value!!)
      val allEntries = StringBuilder()

      for (i in entries.indices) {
        allEntries.append(p.entries[p.findIndexOfValue(entries[i])])
          .append(", ")
      }

      if (allEntries.isNotEmpty()) {
        allEntries.deleteCharAt(allEntries.length - 2)
      }
      p.setSummary(allEntries.toString())
    }
  }

  private fun updateNightMode(p: Preference) {
    if (p is SwitchPreference) {
      if (p.isChecked) {
        p.setSummary(getString(R.string.pref_theme_checked_summary))
        p.setDefaultValue(getString(R.string.pref_theme_true_value))
      } else {
        p.setSummary(getString(R.string.pref_theme_unchecked_summary))
        p.setDefaultValue(getString(R.string.pref_theme_false_value))
      }
    }
  }

  override fun onResume() {
    super.onResume()
    preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
  }

  override fun onPause() {
    preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    super.onPause()
  }

  override fun onStop() {
    preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    super.onStop()
  }

  private fun restartActivity() {
    val intent = Intent(activity, SettingsActivity::class.java)
    startActivity(intent)
    activity!!.finish()
  }
}
