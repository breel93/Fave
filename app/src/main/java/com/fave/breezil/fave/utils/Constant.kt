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
package com.fave.breezil.fave.utils

import android.content.Context
import android.content.SharedPreferences
import com.fave.breezil.fave.BuildConfig
import com.fave.breezil.fave.R
import java.text.SimpleDateFormat
import java.util.LinkedList
import java.util.Calendar
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.collections.List
import kotlin.collections.shuffle

class Constant {
  companion object {
    const val NEWS_API_KEY = BuildConfig.NEWS_API_KEY
    const val BASE_URL = BuildConfig.BASE_URL
    const val ARTICLE = "article"
    const val ARTICLE_TITLE = "article_title"
    const val ARTICLE_URL = "article_url"
    const val BOOKMARK = "bookmark"
    const val SOURCENAME = "source"
    const val CATEGORYNAME = "category"
    const val SEARCH_HINT = "search eg bitcoin, google, politics .."
    const val ARTICLE_LIST = "article_list"
    const val FRAGMENT_TYPE = "fragment_type"
    const val PREFERED_FRAGEMENT = "Preferred"
    const val HEADLINE_FRAGMENT = "Top Stories"
    const val BOOKMARK_FRAGMENT = "BookMarks"
    const val BOOKMARK_ID = "BookMarksID"
    const val SEARCH_RESULT = "Search Result"
    const val ABOUT = "About"
    const val DEFAULT_SOURCE = "bbc-news,axios,cnn,daily-mail,espn,google-news,the-new-york-times"
    const val FAVE_DB = "favnew.db"

    const val ZERO = 0
    const val ONE = 1
    const val TWO = 2
    const val THREE = 3
    const val FIVE_HUNDRED = 500
    const val ONE_THOUSAND = 2000
    const val TWO_THOUSAND = 1000
    const val FOUR = 4
    const val FIVE = 5
    const val TEN = 10
    const val ONE_HUNDRED = 100
    const val DELAY = 800

    fun pickNRandom(lst: ArrayList<String>, n: Int): List<String> {
      val copy = LinkedList(lst)
      copy.shuffle()
      return copy.subList(0, n)
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

    fun sourcesPreferenceList(context: Context, sharedPreferences: SharedPreferences): String {

      val sourceSet = HashSet<String>()
      sourceSet.add(context.getString(R.string.pref_sources_all_value))

      val sharedSet = sharedPreferences.getStringSet(context.getString(R.string.pref_source_key),sourceSet)
      val entries = arrayListOf<String>(sharedSet.toString())
      val selectedSources = StringBuilder()

      for (i in entries.indices) {
        selectedSources.append(entries[i]).append(context.getString(R.string.comma))
      }

      if (selectedSources.isNotEmpty()) {
        selectedSources.deleteCharAt(selectedSources.length - ONE)
      }

      return if (!selectedSources.isBlank() && selectedSources.isNotEmpty() && selectedSources.isNullOrEmpty()) {
        selectedSources.toString()
      } else {

        DEFAULT_SOURCE
      }
    }

    fun getCountry(context: Context, sharedPreferences: SharedPreferences): String? {
      return sharedPreferences.getString(context.getString(R.string.country_key),context.getString(R.string.us))
    }
  }
}
