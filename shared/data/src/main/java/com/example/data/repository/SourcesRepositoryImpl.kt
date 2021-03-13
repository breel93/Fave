package com.example.data.repository

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import com.example.data.remote.SourcesRemote
import com.kolaemiola.domain.repository.SourcesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SourcesRepositoryImpl @Inject constructor(
  private val sourcesRemote: SourcesRemote
): SourcesRepository{
  override fun getSources(
    category: String,
    language: String,
    country: String
  ): Flow<Either<Error, List<Article>>> {
    TODO("Not yet implemented")
  }

}