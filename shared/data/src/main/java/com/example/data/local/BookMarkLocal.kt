package com.example.data.local

import com.example.core.model.Article


interface BookMarkLocal {
  suspend fun getBookMarks(): List<Article>
  suspend fun deleteBookMark(article: Article)
  suspend fun saveBookMark(article: Article)
  suspend fun deleteAllBookMark()
}
