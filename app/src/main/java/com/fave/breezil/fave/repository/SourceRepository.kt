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

import com.fave.breezil.fave.api.EndPointRepository
import com.fave.breezil.fave.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceRepository @Inject
internal constructor(private var endpointRepository: EndPointRepository) {

  suspend fun fetchSources(
    category: String,
    language: String,
    country: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) = flow {
    val response = endpointRepository.fetchSources(category, language, country)
    if (response.status == Result.Status.SUCCESS) {
      val sources = response.data!!.sources
      emit(sources)
      onSuccess()
    } else if (response.status == Result.Status.ERROR) {
      onError(response.message!!)
    }

  }.flowOn(Dispatchers.IO)
}