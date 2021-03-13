package com.kolaemiola.remote.local

import com.example.core.model.Article
import com.example.data.local.BookMarkLocal
import com.kolaemiola.remote.mapper.ArticleLocalMapper
import com.kolaemiola.remote.room.dao.ArticleDao
import javax.inject.Inject

class BookMarkLocalImpl @Inject constructor(
  private val articleDao: ArticleDao,
  private val articleLocalMapper: ArticleLocalMapper
): BookMarkLocal{
  override suspend fun getBookMarks(): List<Article> {
    val localArticle = articleDao.getArticles()
    return articleLocalMapper.mapToDataList(localArticle)
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