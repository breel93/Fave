package com.kolaemiola.data.repository

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.Error
import com.kolaemiola.data.remote.HeadLinesRemote
import com.kolaemiola.domain.repository.HeadLinesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HeadLinesRepositoryImpl @Inject constructor(
  private val headLinesRemote: HeadLinesRemote
): HeadLinesRepository{
  override fun getHeadLines(
    country: String,
    sources: String,
    category: String?,
    query: String,
    pageSize: Int,
    page: Int
  ): Flow<Either<Error, List<Article>>> = flow{
    emit(headLinesRemote.getHeadLines(country = country, sources = sources, category = category,
      query = query, pageSize = pageSize, page = page))
  }

}