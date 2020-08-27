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
package com.fave.breezil.fave.ui.main.bookmark

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.repository.BookMarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookMarkViewModel @Inject
constructor(private val bookMarkRepository: BookMarkRepository) :
  ViewModel() {

  val errorMessages = MutableLiveData<String>()
  val isLoading: ObservableBoolean = ObservableBoolean(false)

  val bookmarkList: LiveData<List<Article>>
    get() = bookMarkRepository.getBookMarks.flowOn(Dispatchers.Main)
      .asLiveData(context = viewModelScope.coroutineContext)

  fun insert(article: Article)= viewModelScope.launch {
    bookMarkRepository.insert(article)
  }

  fun delete(article: Article)  = viewModelScope.launch {
    bookMarkRepository.delete(article)
  }

}
