package com.kolaemiola.domain.repository

import com.example.core.model.Article
import kotlinx.coroutines.flow.Flow

interface BookMarkRepository {
  fun getBookMarks(): Flow<List<Article>>
  suspend fun saveBookMark(article: Article)
  suspend fun deleteBookMark(article: Article)
}