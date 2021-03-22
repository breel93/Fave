package com.kolaemiola.domain.usecases.headline

import arrow.core.Either
import com.kolaemiola.core.model.CategoryArticle
import com.kolaemiola.core.model.Error
import com.kolaemiola.domain.repository.HeadLinesRepository
import com.kolaemiola.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryHeadLinesUseCase @Inject constructor(
  private val headLinesRepository: HeadLinesRepository
): FlowUseCase<CategoryHeadLinesParam, List<CategoryArticle>>(){
  override fun execute(param: CategoryHeadLinesParam): Flow<Either<Error, List<CategoryArticle>>> {
    return headLinesRepository.getCategoriesHeadLines(country = param.country)
  }
}
