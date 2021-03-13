package com.example.data.repository

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import com.example.data.remote.HeadLinesRemote
import com.kolaemiola.domain.repository.HeadLinesRepository
import kotlinx.coroutines.flow.Flow
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
  ): Flow<Either<Error, List<Article>>> {
    TODO("Not yet implemented")
  }

}