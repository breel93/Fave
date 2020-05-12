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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.repository.BookMarkRepository
import javax.inject.Inject

class BookMarkViewModel @Inject
constructor(private val bookMarkRepository: BookMarkRepository, application: Application) :
  AndroidViewModel(application) {

  val bookmarkList: LiveData<List<Article>> = bookMarkRepository.getBookMarks()

  fun insert(article: Article) {
    bookMarkRepository.insertBookMark(article)
  }

//  fun insertIt(article: Article) : LiveData<String>{
//    return bookMarkRepository.insertBookMark(article).observe
//  }

  fun delete(article: Article) {
    bookMarkRepository.deleteBookMark(article)
  }

  fun deleteAll() {
    bookMarkRepository.deleteAllBookMark()
  }
}
