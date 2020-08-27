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
package com.fave.breezil.fave.api

import com.fave.breezil.fave.BuildConfig.NEWS_API_KEY
import javax.inject.Inject

class EndPointRepository @Inject
constructor(private val newsApi: NewsApi) : BaseDataSource() {
  suspend fun fetchHeadline(
    country: String,
    sources: String,
    category: String,
    query: String,
    pageSize: Int,
    page: Int
  ) = getResult {
    newsApi.getHeadLines(country, sources, category, query, pageSize, page, NEWS_API_KEY)
  }

  suspend fun fetchEverything(
    query: String, sources: String, sortBy: String,
    from: String, to: String, language: String, pageSize: Int,
    page: Int
  ) = getResult {
    newsApi.getEverything(
      query, sources, sortBy, from, to, language, pageSize,
      page, NEWS_API_KEY
    )
  }

  suspend fun fetchSources(
    category: String,
    language: String,
    country: String
  ) = getResult {
    newsApi.getSources(category, language, country, NEWS_API_KEY)
  }


}
