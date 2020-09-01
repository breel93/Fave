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
package com.fave.breezil.fave.ui.main.top_stories

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.ParentModel
import com.fave.breezil.fave.repository.HeadlineRepository
import com.fave.breezil.fave.repository.NetworkState
import com.fave.breezil.fave.repository.headlines.HeadlineDataSource
import com.fave.breezil.fave.repository.headlines.HeadlineDataSourceFactory
import com.fave.breezil.fave.utils.Constant.Companion.TEN
import com.fave.breezil.fave.utils.LiveCoroutinesViewModel
import com.fave.breezil.fave.utils.helpers.AppExecutors

class MainViewModel @ViewModelInject
constructor(
  private val headlineDataSourceFactory: HeadlineDataSourceFactory,
  private val appsExecutor: AppExecutors,
  private val headlineRepository: HeadlineRepository
) : LiveCoroutinesViewModel() {

  val errorMessages = MutableLiveData<String>()
  val isLoading: ObservableBoolean = ObservableBoolean(false)

  var articleList: LiveData<PagedList<Article>>
  private val networkState: LiveData<NetworkState> = Transformations.switchMap(
    headlineDataSourceFactory.headlineDataSourceMutableLiveData, HeadlineDataSource::mNetworkState
  )
  private val initialLoading: LiveData<NetworkState> = Transformations.switchMap(
    headlineDataSourceFactory.headlineDataSourceMutableLiveData, HeadlineDataSource::mInitialLoading
  )

  init {

    val config = PagedList.Config.Builder()
      .setEnablePlaceholders(true)
      .setInitialLoadSizeHint(TEN)
      .setPrefetchDistance(TEN)
      .setPageSize(TEN)
      .build()

    articleList = LivePagedListBuilder(headlineDataSourceFactory, config)
      .setFetchExecutor(appsExecutor.networkIO())
      .build()
  }

  fun setParameter(country: String, sources: String, category: String, query: String) {
    headlineDataSourceFactory.headlineDataSource.country = country
    headlineDataSourceFactory.headlineDataSource.sources = sources
    headlineDataSourceFactory.headlineDataSource.category = category
    headlineDataSourceFactory.headlineDataSource.query = query
  }

  fun getNetworkState(): LiveData<NetworkState> {
    return networkState
  }
  val initialLoadingState: LiveData<NetworkState>
    get() {
      return initialLoading
    }

  fun refreshArticle(): LiveData<PagedList<Article>> {
    val config = PagedList.Config.Builder()
      .setEnablePlaceholders(true)
      .setPageSize(TEN)
      .build()

    articleList = LivePagedListBuilder(headlineDataSourceFactory, config)
      .setFetchExecutor(appsExecutor.networkIO())
      .build()

    return articleList
  }


  fun getCategoryList(
    context: Context,
    mArrayList: ArrayList<ParentModel>,
    country: String,
    sources: String,
    query: String
  ) = launchOnViewModelScope{
    headlineRepository.getCategoriesArticle(context, mArrayList, country, sources, query).asLiveData()
  }

  fun getBreakingNewList(
    sources: String,
    sortBy: String,
    from: String,
    to: String,
    language: String
  ) = launchOnViewModelScope {
    headlineRepository.fetchBreakingNewsArticles(sources, sortBy, from, to, language,
      onSuccess = {isLoading.set(false)},
      onError = {errorMessages.postValue(it)}
    ).asLiveData()
  }
}
