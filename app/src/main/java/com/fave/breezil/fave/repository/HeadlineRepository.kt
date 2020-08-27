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
import com.fave.breezil.fave.R
import com.fave.breezil.fave.api.EndPointRepository
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.ParentModel
import com.fave.breezil.fave.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@SuppressLint("CheckResult")

@Singleton
class HeadlineRepository @Inject
internal constructor(private var endpointRepository: EndPointRepository ) {



  suspend fun getCategoriesArticle(
    context: Context,
    parentList: ArrayList<ParentModel>,
    country: String,
    sources: String,
    query: String
  ) = flow {
    val textArray = context.resources.getStringArray(R.array.category_list)
    val trendCategoryList = listOf(*textArray)
    for (item in (trendCategoryList as MutableList<String>?)!!) {
      val parentModel = ParentModel()
      val articles = ArrayList<Article>()
      parentModel.title = item
      val response = endpointRepository.fetchHeadline(country, sources, item, query, 3, 1)
      if (response.status == Result.Status.SUCCESS) {
        articles.addAll(response.data!!.articles)
        parentModel.articles = articles
        emit(true)
      }else{
        emit(false)
      }
      parentList.add(parentModel)
    }
  }.flowOn(Dispatchers.IO)


  suspend fun fetchBreakingNewsArticles(
    sources: String,
    sortBy: String,
    from: String,
    to: String,
    language: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) = flow{
    val response = endpointRepository.fetchEverything("", sources, sortBy, from, to, language, 10, 1)
    if (response.status == Result.Status.SUCCESS) {
      val articles = response.data!!.articles
      emit(articles)
      onSuccess()
    } else if (response.status == Result.Status.ERROR) {
      onError(response.message!!)
    }
  }.flowOn(Dispatchers.IO)
}
