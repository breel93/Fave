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
import com.fave.breezil.fave.repository.NetworkState
import com.fave.breezil.fave.utils.Constant.Companion.ONE
import com.fave.breezil.fave.utils.Constant.Companion.TWO
import com.fave.breezil.fave.utils.Constant.Companion.ZERO
import com.fave.breezil.fave.utils.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EverythingDataSource @Inject
constructor(
  private var endpointRepository: EndPointRepository,
  private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Article>() {

  var mNetworkState = MutableLiveData<NetworkState>()
  var mInitialLoading = MutableLiveData<NetworkState>()

  lateinit var query: String
  lateinit var sources: String
  lateinit var sortBy: String
  lateinit var from: String
  lateinit var to: String
  lateinit var language: String

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
  }
  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, Article>
  ) {
    mNetworkState.postValue(NetworkState.LOADING)
    mInitialLoading.postValue(NetworkState.LOADING)
    fetchData(query, sources, sortBy, from, to, language, params.requestedLoadSize,
      ONE){
      callback.onResult(it, null, TWO)
    }
  }



  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    mNetworkState.postValue(NetworkState.LOADING)
    val page = params.key
    fetchData(query, sources, sortBy, from, to, language, params.requestedLoadSize,
      page){
      callback.onResult(it,  page + ONE)
    }
  }

  private fun fetchData(query: String, sources: String, sortBy: String,
                        from: String, to: String, language: String, pageSize: Int,
                        page: Int, callback: (List<Article>) -> Unit){
    scope.launch(getJobErrorHandler()) {
      val response = endpointRepository.fetchEverything(query, sources, sortBy, from, to, language, pageSize,
        page)
      if(response.status == Result.Status.SUCCESS){
        val results = response.data!!.articles
        callback(results)
        if(results.size > ZERO){
          mInitialLoading.postValue(NetworkState.LOADED)
          mNetworkState.postValue(NetworkState.LOADED)
        }else{
          mInitialLoading.postValue(NetworkState(NetworkState.Status.NO_RESULT))
          mNetworkState.postValue(NetworkState(NetworkState.Status.NO_RESULT))
        }
      }else if(response.status == Result.Status.ERROR){
        postError(response.message!!)
      }
    }

  }

  private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
    postError(e.message ?: e.toString())
  }

  private fun postError(message: String) {
    Timber.e("An error happened: $message")
    // TODO network error handling
    mNetworkState.postValue(NetworkState(NetworkState.Status.FAILED))
  }
}
