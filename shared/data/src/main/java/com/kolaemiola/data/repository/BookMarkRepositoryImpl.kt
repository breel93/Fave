package com.kolaemiola.data.repository


import com.kolaemiola.core.model.Article
import com.kolaemiola.data.local.BookMarkLocal
import com.kolaemiola.domain.repository.BookMarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookMarkRepositoryImpl @Inject constructor(
  private val bookMarkLocal: BookMarkLocal
) : BookMarkRepository {
  override fun getBookMarks(): Flow<List<Article>> = flow {
    emit(bookMarkLocal.getBookMarks())
  }

  override suspend fun saveBookMark(article: Article) {
    bookMarkLocal.saveBookMark(article)
  }

  override suspend fun deleteBookMark(article: Article) {
    bookMarkLocal.deleteBookMark(article)
  }

}