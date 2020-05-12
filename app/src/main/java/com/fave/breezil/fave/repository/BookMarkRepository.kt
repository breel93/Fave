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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fave.breezil.fave.db.ArticleDao
import com.fave.breezil.fave.model.Article
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookMarkRepository @Inject
constructor(private val articleDao: ArticleDao) {
  private val isSuccessful = MutableLiveData<String>()
  fun getBookMarks(): LiveData<List<Article>> {
    return articleDao.allBookMarks
  }

  fun insertBookMark(article: Article): MutableLiveData<String> {
    Completable.fromAction { articleDao.insert(article) }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(object : CompletableObserver {
        override fun onComplete() {
          isSuccessful.postValue("Successful")
        }

        override fun onSubscribe(d: Disposable) {
          d.dispose()
        }

        override fun onError(e: Throwable) {
          isSuccessful.postValue(e.message)
        }
      })
    return isSuccessful
  }

  fun deleteBookMark(article: Article): MutableLiveData<String> {
    Completable.fromAction { articleDao.delete(article) }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        object : CompletableObserver {
          override fun onComplete() {
            isSuccessful.postValue("successful")
          }

          override fun onSubscribe(d: Disposable) {
            d.dispose()
          }

          override fun onError(e: Throwable) {
            isSuccessful.postValue(e.message)
          }
        }
      )
    return isSuccessful
  }

  fun deleteAllBookMark(): MutableLiveData<String> {
    Completable.fromAction { articleDao.deleteAllArticle() }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        object : CompletableObserver {
          override fun onComplete() {
            isSuccessful.postValue("successful")
          }

          override fun onSubscribe(d: Disposable) {
            d.dispose()
          }

          override fun onError(e: Throwable) {
            isSuccessful.postValue(e.message)
          }
        }
      )
    return isSuccessful
  }
}
