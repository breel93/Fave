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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.repository.headlines.SourceRepository
import javax.inject.Inject

class SourcesViewModel @Inject
constructor(private val sourceRepository: SourceRepository, application: Application) : AndroidViewModel(application) {

  fun getSourcesList(category: String, language: String, country: String): MutableLiveData<List<Sources>> {
    return sourceRepository.getSources(category, language, country)
  }
}
