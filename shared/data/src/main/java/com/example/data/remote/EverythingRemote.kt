package com.example.data.remote

import com.example.core.model.Article
import arrow.core.Either
import com.example.core.model.Error

interface EverythingRemote {
  suspend fun getEverything(
      query: String, sources: String, sortBy: String,
      from: String, to: String, language: String, pageSize: Int,
      page: Int
  ): Either<Error, List<Article>>
}
