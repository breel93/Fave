package com.kolaemiola.domain.repository

import arrow.core.Either
import com.kolaemiola.core.model.Error
import com.kolaemiola.core.model.Source
import kotlinx.coroutines.flow.Flow

interface SourcesRepository {
  fun getSources(category: String, language: String, country: String): Flow<Either<Error, List<Source>>>
}