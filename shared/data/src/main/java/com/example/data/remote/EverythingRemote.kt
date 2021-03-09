package com.example.data.remote

import com.example.core.model.Article

interface EverythingRemote {
  suspend fun getEverything(
      query: String, sources: String, sortBy: String,
      from: String, to: String, language: String, pageSize: Int,
      page: Int
  ): List<Article>
}