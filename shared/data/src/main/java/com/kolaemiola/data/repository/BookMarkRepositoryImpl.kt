package com.example.data.repository

import com.example.core.model.Article
import com.example.data.local.BookMarkLocal
import com.kolaemiola.domain.repository.BookMarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookMarkRepositoryImpl @Inject constructor(
  private val bookMarkLocal: BookMarkLocal
): BookMarkRepository{
  override fun getBookMarks(): Flow<List<Article>> {
    TODO("Not yet implemented")
  }

  override suspend fun saveBookMark(article: Article) {
    TODO("Not yet implemented")
  }

  override suspend fun deleteBookMark(article: Article) {
    TODO("Not yet implemented")
  }

}