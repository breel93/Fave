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
package com.fave.breezil.fave.repository.headlines

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.fave.breezil.fave.BuildConfig.NEWS_API_KEY
import com.fave.breezil.fave.api.NewsApi
import com.fave.breezil.fave.model.Sources
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceRepository @Inject
internal constructor(private val newsApi: NewsApi) {

  private val sourceList = MutableLiveData<List<Sources>>()

  @SuppressLint("CheckResult")
  fun getSources(
    category: String,
    language: String,
    country: String
  ): MutableLiveData<List<Sources>> {
    newsApi.getSources(category, language, country, NEWS_API_KEY)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        it.sources
        sourceList.postValue(it.sources)
      }, { throwable -> throwable.printStackTrace() })

    return sourceList
  }
}
