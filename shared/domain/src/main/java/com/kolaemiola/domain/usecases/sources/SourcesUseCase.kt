package com.kolaemiola.domain.usecases.sources

import arrow.core.Either
import com.kolaemiola.core.model.Error
import com.kolaemiola.core.model.Source
import com.kolaemiola.domain.repository.SourcesRepository
import com.kolaemiola.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SourcesUseCase @Inject constructor(
  private val sourcesRepository: SourcesRepository
) : FlowUseCase<SourceQueryParam, List<Source>>() {
  override fun execute(param: SourceQueryParam): Flow<Either<Error, List<Source>>> {
    return sourcesRepository.getSources(
      category = param.category,
      language = param.language,
      country = param.country,
    )
  }
}
