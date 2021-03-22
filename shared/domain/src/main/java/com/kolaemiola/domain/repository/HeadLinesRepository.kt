package com.kolaemiola.domain.repository

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.CategoryArticle
import com.kolaemiola.core.model.Error
import kotlinx.coroutines.flow.Flow

interface HeadLinesRepository {
  fun getHeadLines(country: String, sources: String, category: String?,
                   query: String, pageSize:Int, page: Int): Flow<Either<Error, List<Article>>>

  fun getCategoriesHeadLines(country: String): Flow<Either<Error, List<CategoryArticle>>>
}