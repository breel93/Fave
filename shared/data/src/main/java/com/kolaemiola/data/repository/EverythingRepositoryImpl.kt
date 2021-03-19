package com.kolaemiola.data.repository

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.Error
import com.kolaemiola.data.remote.EverythingRemote
import com.kolaemiola.domain.repository.EverythingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
  ): Flow<Either<Error, List<Article>>> = flow {
    val everything = sources.takeIf { it.isNullOrEmpty()}
      ?.asSequence()
      ?.filterNot { it.isEmpty() }
      ?.map { it.trim() }
      ?.joinToString(",")
      ?.let { newSources ->
        everythingRemote.getEverything(query = query,  sources = newSources, sortBy = sortBy,
          from = from, to = to, language = language, pageSize = pageSize, page = page)
      }?: Either.left(
        Error(message = "List can not be empty")
      )
    emit(everything)
  }

}