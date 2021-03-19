package com.kolaemiola.data.local

import com.kolaemiola.core.model.Article


interface BookMarkLocal {
  suspend fun getBookMarks(): List<Article>
  suspend fun deleteBookMark(article: Article)
  suspend fun saveBookMark(article: Article)
  suspend fun deleteAllBookMark()
}
