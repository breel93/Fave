package com.kolaemiola.domain.usecases.headline

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.Error
import com.kolaemiola.domain.repository.HeadLinesRepository
import com.kolaemiola.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HeadLineUseCase @Inject constructor(
  private val headLinesRepository: HeadLinesRepository
) : FlowUseCase<HeadLineQueryParam, List<Article>>() {
  override fun execute(param: HeadLineQueryParam): Flow<Either<Error, List<Article>>> {
    return headLinesRepository.getHeadLines(
      country = param.country,
      sources = param.sources,
      category = param.category,
      query = param.query,
      pageSize = param.pageSize,
      page = param.page
    )
  }
}