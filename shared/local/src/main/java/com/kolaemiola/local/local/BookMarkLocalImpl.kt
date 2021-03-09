package com.kolaemiola.local.local

import com.example.core.model.Article
import com.example.data.local.BookMarkLocal
import com.kolaemiola.local.mapper.ArticleLocalMapper
import com.kolaemiola.local.room.dao.ArticleDao
import javax.inject.Inject

class BookMarkLocalImpl @Inject constructor(
  private val articleDao: ArticleDao,
  private val articleLocalMapper: ArticleLocalMapper
): BookMarkLocal{
  override suspend fun getBookMarks(): List<Article> {
    TODO("Not yet implemented")
  }

}