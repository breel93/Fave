package com.kolaemiola.domain.fake

import arrow.core.Either
import com.kolaemiola.core.model.Error
import com.kolaemiola.core.model.Source
import com.kolaemiola.domain.repository.SourcesRepository
import com.kolaemiola.domain.utils.MockData
import com.kolaemiola.domain.utils.ResponseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.net.SocketTimeoutException

internal class SourceRepositoryFake : SourcesRepository{
  companion object {
    const val ERROR_MSG: String = "No network"
  }

  private var sourcesflow: Flow<Either<Error, List<Source>>> = flow {
    emit(Either.right(MockData.mockSources))
  }

  var responseType: ResponseType = ResponseType.DATA
    set(value) {
      field = value
      sourcesflow = makeResponse(value)
    }
  private fun makeResponse(type: ResponseType): Flow<Either<Error, List<Source>>> {
    return when (type) {
      ResponseType.DATA -> flowOf(Either.right(MockData.mockSources))
      ResponseType.EMPTY -> flowOf(Either.right(listOf()))
      ResponseType.ERROR -> flow { Either.left(Error(message = "Unable to get source")) }
      ResponseType.NETWORKERROR -> flow { throw SocketTimeoutException(
        HeadlineRepositoryFake.ERROR_MSG
      ) }
    }
  }

  override fun getSources(
    category: String,
    language: String,
    country: String
  ): Flow<Either<Error, List<Source>>> = sourcesflow

}