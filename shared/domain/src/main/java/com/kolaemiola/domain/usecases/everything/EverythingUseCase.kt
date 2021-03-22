package com.kolaemiola.domain.usecases.everything

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.Error
import com.kolaemiola.domain.repository.EverythingRepository
import com.kolaemiola.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EverythingUseCase @Inject constructor(
  private val everythingRepository: EverythingRepository
): FlowUseCase<EverythingQueryParam, List<Article>>(){
  override fun execute(param: EverythingQueryParam): Flow<Either<Error, List<Article>>> {
    return everythingRepository.getEverything(
      query = param.query,
      sources = param.sources,
      sortBy = param.sortBy,
      from = param.from,
      to = param.to,
      language = param.language,
      pageSize = param.pageSize,
      page = param.page
    )
  }
}