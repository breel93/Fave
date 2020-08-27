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
package com.fave.breezil.fave.ui.main.look_up

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.repository.NetworkState
import com.fave.breezil.fave.repository.everything.EverythingDataSource
import com.fave.breezil.fave.repository.everything.EverythingDataSourceFactory
import com.fave.breezil.fave.utils.Constant.Companion.TEN
import com.fave.breezil.fave.utils.helpers.AppExecutors
import javax.inject.Inject

class LookUpViewModel @Inject
constructor(
  private val everythingDataSourceFactory: EverythingDataSourceFactory,
  private val appsExecutor: AppExecutors,
  application: Application
) :
  AndroidViewModel(application) {
  var articleList: LiveData<PagedList<Article>>

  private val networkState: LiveData<NetworkState> = switchMap(
    everythingDataSourceFactory.everythingDataSourceMutableLiveData,
    EverythingDataSource::mNetworkState
  )
  private val initialLoading: LiveData<NetworkState> = switchMap(
    everythingDataSourceFactory.everythingDataSourceMutableLiveData,
    EverythingDataSource::mInitialLoading
  )
  init {
    val config = PagedList.Config.Builder()
      .setEnablePlaceholders(true)
      .setInitialLoadSizeHint(TEN)
      .setPrefetchDistance(TEN)
      .setPageSize(TEN)
      .build()

    articleList = LivePagedListBuilder(everythingDataSourceFactory, config)
      .setFetchExecutor(appsExecutor.networkIO())
      .build()
  }

  fun setParameter(
    query: String,
    sources: String,
    sortBy: String,
    from: String,
    to: String,
    language: String
  ) {
    everythingDataSourceFactory.everythingDataSource.query = query
    everythingDataSourceFactory.everythingDataSource.sources = sources
    everythingDataSourceFactory.everythingDataSource.sortBy = sortBy
    everythingDataSourceFactory.everythingDataSource.from = from
    everythingDataSourceFactory.everythingDataSource.to = to
    everythingDataSourceFactory.everythingDataSource.language = language
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

    articleList = LivePagedListBuilder(everythingDataSourceFactory, config)
      .setFetchExecutor(appsExecutor.networkIO())
      .build()

    return articleList
  }

}
