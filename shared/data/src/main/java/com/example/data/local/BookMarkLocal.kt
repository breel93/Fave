package com.example.data.local

import com.example.core.model.Article


interface BookMarkLocal {
  suspend fun getBookMarks(): List<Article>
}
