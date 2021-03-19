package com.kolaemiola.data.repository

import arrow.core.Either
import com.kolaemiola.core.model.Error
import com.kolaemiola.core.model.Source
import com.kolaemiola.data.remote.SourcesRemote
import com.kolaemiola.domain.repository.SourcesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SourcesRepositoryImpl @Inject constructor(
  private val sourcesRemote: SourcesRemote
): SourcesRepository{
  override fun getSources(
    category: String,
    language: String,
    country: String
  ): Flow<Either<Error, List<Source>>> = flow{
    emit(sourcesRemote.getSources(category, language, country))
  }
}