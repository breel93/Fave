package com.kolaemiola.data.repository


import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.Error
import com.kolaemiola.data.local.BookMarkLocal
import com.kolaemiola.domain.repository.BookMarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookMarkRepositoryImpl @Inject constructor(
  private val bookMarkLocal: BookMarkLocal
) : BookMarkRepository {
  override fun getBookMarks(): Flow<Either<Error, List<Article>>>  = flow {
    val bookMarkedArticles = bookMarkLocal.getBookMarks()
    if(bookMarkedArticles.isNotEmpty()){
      emit(Either.right(bookMarkedArticles))
    }else{
      emit(Either.left(Error(message = "No bookmarks saved")))
    }
  }

  override suspend fun saveBookMark(article: Article) {
    bookMarkLocal.saveBookMark(article)
  }

  override suspend fun deleteBookMark(article: Article) {
    bookMarkLocal.deleteBookMark(article)
  }

}