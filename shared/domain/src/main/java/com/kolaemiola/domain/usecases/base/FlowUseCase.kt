package com.kolaemiola.domain.usecases.base

import arrow.core.Either
import com.kolaemiola.core.model.Error
import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in Param, out Result> {

  abstract fun execute(param: Param): Flow<Either<Error, Result>>
}