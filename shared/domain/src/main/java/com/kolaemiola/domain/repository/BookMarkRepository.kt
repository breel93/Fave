package com.kolaemiola.domain.repository

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.Error
import kotlinx.coroutines.flow.Flow

interface BookMarkRepository {
  fun getBookMarks(): Flow<Either<Error, List<Article>>>
  suspend fun saveBookMark(article: Article)
  suspend fun deleteBookMark(article: Article)
}