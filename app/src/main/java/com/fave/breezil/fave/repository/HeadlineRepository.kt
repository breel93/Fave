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
package com.fave.breezil.fave.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.fave.breezil.fave.BuildConfig.NEWS_API_KEY
import com.fave.breezil.fave.R
import com.fave.breezil.fave.api.NewsApi
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.ParentModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Collections
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@SuppressLint("CheckResult")

@Singleton
class HeadlineRepository @Inject
internal constructor(private val newsApi: NewsApi) {

  private var trendCategoryList: List<String>? = null
  private var isSuccessful = MutableLiveData<Boolean>()
  private val breakingNewList = MutableLiveData<List<Article>>()

  fun getArticleSuccessful(
    context: Context,
    parentList: ArrayList<ParentModel>,
    country: String,
    sources: String,
    query: String
  ): MutableLiveData<Boolean> {
    val textArray = context.resources.getStringArray(R.array.category_list)
    trendCategoryList = listOf(*textArray)
    Collections.shuffle(trendCategoryList)

    for (item in (trendCategoryList as MutableList<String>?)!!) {

      val parentModel = ParentModel()
      val articles = ArrayList<Article>()
      parentModel.title = item
      newsApi.getHeadlines(country, sources, item, query, 3, 1, NEWS_API_KEY)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          articles.addAll(it.articles)
          isSuccessful.postValue(true)
        },
          { throwable -> throwable.printStackTrace() })

      parentModel.articles = articles
      parentList.add(parentModel)
    }

    return isSuccessful
  }

  fun getBreakingNewsArticles(
    sources: String,
    sortBy: String?,
    from: String?,
    to: String?,
    language: String?
  ):
      MutableLiveData<List<Article>> {
    newsApi.getBreakingNews("", sources, sortBy, from, to, language, 20, 1, NEWS_API_KEY)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        breakingNewList.postValue(it.articles)
      },
        { throwable -> throwable.printStackTrace() })
    return breakingNewList
  }
}
