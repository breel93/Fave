package com.example.data.repository

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import com.example.data.remote.EverythingRemote
import com.kolaemiola.domain.repository.EverythingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EverythingRepositoryImpl @Inject constructor(
  private val everythingRemote: EverythingRemote
): EverythingRepository{
  override fun getEverything(
    query: String,
    sources: List<String>,
    sortBy: String,
    from: String,
    to: String,
    language: String,
    pageSize: Int,
    page: Int
  ): Flow<Either<Error, List<Article>>> {
    TODO("Not yet implemented")
  }

}