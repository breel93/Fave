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
package com.fave.breezil.fave.repository.everything

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.fave.breezil.fave.api.EndPointRepository
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.ArticleResult
import com.fave.breezil.fave.repository.NetworkState
import com.fave.breezil.fave.repository.PaginationListener
import com.fave.breezil.fave.utils.Constant.Companion.ONE
import com.fave.breezil.fave.utils.Constant.Companion.TEN
import com.fave.breezil.fave.utils.Constant.Companion.TWO
import com.fave.breezil.fave.utils.Constant.Companion.ZERO
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EverythingDataSource @Inject
constructor(
  private var endpointRepository: EndPointRepository,
  private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Article>(), PaginationListener<ArticleResult, Article> {

  var mNetworkState = MutableLiveData<NetworkState>()
  var mInitialLoading = MutableLiveData<NetworkState>()

  lateinit var query: String
  lateinit var sources: String
  lateinit var sortBy: String
  lateinit var from: String
  lateinit var to: String
  lateinit var language: String

  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, Article>
  ) {
    mNetworkState.postValue(NetworkState.LOADING)
    mInitialLoading.postValue(NetworkState.LOADING)
    val articlesList = ArrayList<Article>()
    val articles = endpointRepository.getEverything(
      query, sources, sortBy, from, to, language,
      TEN, ONE
    ).subscribe({ articleResult -> onInitialSuccess(articleResult, callback, articlesList) },
      { throwable -> onInitialError(throwable) })
    compositeDisposable.add(articles)
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    mNetworkState.postValue(NetworkState.LOADING)
    val articlesList = ArrayList<Article>()
    val articles = endpointRepository.getEverything(
      query, sources, sortBy, from, to, language,
      TEN, params.key
    ).subscribe({ articleResult ->
      onPaginationSuccess(
        articleResult,
        callback,
        params,
        articlesList
      )
    }, { throwable -> onPaginationError(throwable) })
    compositeDisposable.add(articles)
  }

  override fun onInitialError(throwable: Throwable) {
    mInitialLoading.postValue(NetworkState(NetworkState.Status.FAILED))
    mNetworkState.postValue(NetworkState(NetworkState.Status.FAILED))
    Timber.e(throwable)
  }

  override fun onInitialSuccess(
    response: ArticleResult,
    callback: LoadInitialCallback<Int, Article>,
    results: MutableList<Article>
  ) {
    if (response.articles.size > ZERO) {
      results.addAll(response.articles)
      callback.onResult(results, null, TWO)
      mInitialLoading.postValue(NetworkState.LOADED)
      mNetworkState.postValue(NetworkState.LOADED)
    } else {
      mInitialLoading.postValue(NetworkState(NetworkState.Status.NO_RESULT))
      mNetworkState.postValue(NetworkState(NetworkState.Status.NO_RESULT))
    }
  }

  override fun onPaginationError(throwable: Throwable) {
    mNetworkState.postValue(NetworkState(NetworkState.Status.FAILED))
    Timber.e(throwable)
  }

  override fun onPaginationSuccess(
    response: ArticleResult,
    callback: LoadCallback<Int, Article>,
    params: LoadParams<Int>,
    results: MutableList<Article>
  ) {
    if (response.articles.size > ZERO) {
      results.addAll(response.articles)

      val key = (if (params.key > ONE) params.key + ONE else null)!!.toInt()
      callback.onResult(results, key)
      mNetworkState.postValue(NetworkState.LOADED)
    }
  }

  override fun clear() {
    compositeDisposable.clear()
  }
}
