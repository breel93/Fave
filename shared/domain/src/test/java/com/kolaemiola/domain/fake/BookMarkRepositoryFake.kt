package com.kolaemiola.domain.fake

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.Error
import com.kolaemiola.domain.repository.BookMarkRepository
import com.kolaemiola.domain.utils.MockData
import com.kolaemiola.domain.utils.ResponseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

internal class BookMarkRepositoryFake: BookMarkRepository {
  private var bookmarkflow: Flow<Either<Error, List<Article>>> = flow{
    emit(Either.right(MockData.mockArticles))
  }
  var responseType: ResponseType = ResponseType.DATA
    set(value) {
      field = value
      bookmarkflow = makeResponse(value)
    }

  private fun makeResponse(type: ResponseType): Flow<Either<Error, List<Article>>> {
    return when (type) {
      ResponseType.DATA -> flowOf(Either.right(MockData.mockArticles))
      ResponseType.EMPTY -> flowOf(Either.right(listOf()))
      else -> flowOf(Either.right(listOf()))
    }
  }
  override fun getBookMarks(): Flow<Either<Error, List<Article>>> = bookmarkflow

  override suspend fun saveBookMark(article: Article) {
    TODO("Not yet implemented")
  }

  override suspend fun deleteBookMark(article: Article) {
    TODO("Not yet implemented")
  }

}