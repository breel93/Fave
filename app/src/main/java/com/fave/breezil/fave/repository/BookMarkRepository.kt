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

import androidx.annotation.WorkerThread
import com.fave.breezil.fave.db.ArticleDao
import com.fave.breezil.fave.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookMarkRepository @Inject
constructor(private val articleDao: ArticleDao) {

  val getBookMarks: Flow<List<Article>> = articleDao.allBookMarks()

  @WorkerThread
  suspend fun insert(article: Article) = withContext(Dispatchers.IO) {
    articleDao.insert(article)
  }

  @WorkerThread
  suspend fun delete(article: Article) = withContext(Dispatchers.IO) {
    articleDao.delete(article)
  }
}
