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
package com.fave.breezil.fave.ui.main.sources

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.fave.breezil.fave.repository.SourceRepository
import com.fave.breezil.fave.utils.LiveCoroutinesViewModel

class SourcesViewModel @ViewModelInject
constructor(private val sourceRepository: SourceRepository) : LiveCoroutinesViewModel() {

  val errorMessages = MutableLiveData<String>()
  val isLoading: ObservableBoolean = ObservableBoolean(false)
  fun getSourcesList(category: String, language: String, country: String) = launchOnViewModelScope {
    sourceRepository.fetchSources(
      category, language, country,
      onSuccess = {isLoading.set(false)},
      onError = {errorMessages.postValue(it)}
    ).asLiveData()
  }
}

