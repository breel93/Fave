package com.kolaemiola.domain.repository

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import kotlinx.coroutines.flow.Flow

interface EverythingRepository {
  fun getEverything(query: String, sources: List<String>, sortBy: String,
                    from: String, to: String, language: String, pageSize: Int,
                    page: Int):Flow<Either<Error, List<Article>>>
}