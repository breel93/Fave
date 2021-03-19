package com.kolaemiola.local.local

import com.kolaemiola.core.model.Article
import com.kolaemiola.data.local.BookMarkLocal
import com.kolaemiola.local.mapper.ArticleLocalMapper
import com.kolaemiola.local.room.dao.ArticleDao
import javax.inject.Inject

class BookMarkLocalImpl @Inject constructor(
  private val articleDao: ArticleDao,
  private val articleLocalMapper: ArticleLocalMapper
): BookMarkLocal {
  override suspend fun getBookMarks(): List<Article> {
    val localArticles = articleDao.getArticles()

    return localArticles.map(articleLocalMapper::mapToData)
  }

  override suspend fun deleteBookMark(article: Article) {
    articleDao.delete(articleLocalMapper.mapToLocal(article))
  }

  override suspend fun saveBookMark(article: Article) {
    articleDao.insert(articleLocalMapper.mapToLocal(article))
  }

  override suspend fun deleteAllBookMark() {
    articleDao.deleteAll()
  }

}