package com.kolaemiola.domain.fake

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.CategoryArticle
import com.kolaemiola.core.model.Error
import com.kolaemiola.domain.repository.HeadLinesRepository
import com.kolaemiola.domain.utils.MockData
import com.kolaemiola.domain.utils.ResponseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.net.SocketTimeoutException

internal class HeadlineRepositoryFake : HeadLinesRepository {
  companion object {
    const val ERROR_MSG: String = "No network"
  }

  private var headlineflow: Flow<Either<Error, List<Article>>> = flow {
    emit(Either.right(MockData.mockArticles))
  }

  var responseType: ResponseType = ResponseType.DATA
    set(value) {
      field = value
      headlineflow = makeResponse(value)
    }

  private fun makeResponse(type: ResponseType): Flow<Either<Error, List<Article>>> {
    return when (type) {
      ResponseType.DATA -> flowOf(Either.right(MockData.mockArticles))
      ResponseType.EMPTY -> flowOf(Either.right(listOf()))
      ResponseType.ERROR -> flow { Either.left(Error(message = "Unable to get articles")) }
      ResponseType.NETWORKERROR -> flow { throw SocketTimeoutException(ERROR_MSG) }
    }
  }

  override fun getHeadLines(
    country: String,
    sources: String,
    category: String?,
    query: String,
    pageSize: Int,
    page: Int
  ): Flow<Either<Error, List<Article>>> = headlineflow


  override fun getCategoriesHeadLines(country: String): Flow<Either<Error, List<CategoryArticle>>> {
    TODO("Not yet implemented")
  }
}