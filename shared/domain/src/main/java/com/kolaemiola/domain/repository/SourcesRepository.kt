package com.kolaemiola.domain.repository

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import kotlinx.coroutines.flow.Flow

interface SourcesRepository {
  fun getSources(category: String, language: String, country: String): Flow<Either<Error, List<Article>>>
}