package com.kolaemiola.domain.usecases.bookmark

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.Error
import com.kolaemiola.domain.repository.BookMarkRepository
import com.kolaemiola.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookMarkUseCase @Inject constructor(
  private val bookMarkRepository: BookMarkRepository
): FlowUseCase<String, List<Article>>(){
  override fun execute(param: String): Flow<Either<Error, List<Article>>> {
    return bookMarkRepository.getBookMarks()
  }

}