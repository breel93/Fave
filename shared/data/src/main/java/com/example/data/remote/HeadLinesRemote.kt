package com.example.data.remote

import com.example.core.model.Article

interface HeadLinesRemote {
  suspend fun getHeadLines(
      country: String, sources: String, category: String?,
      query: String, pageSize:Int, page: Int
  ): List<Article>
}
