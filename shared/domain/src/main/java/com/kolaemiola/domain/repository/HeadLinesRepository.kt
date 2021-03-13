package com.kolaemiola.domain.repository

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import kotlinx.coroutines.flow.Flow

interface HeadLinesRepository {
  fun getHeadLines(country: String, sources: String, category: String?,
                   query: String, pageSize:Int, page: Int): Flow<Either<Error, List<Article>>>
}