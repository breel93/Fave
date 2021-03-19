package com.example.data.remote

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error

interface HeadLinesRemote {
  suspend fun getHeadLines(
      country: String, sources: String, category: String?,
      query: String, pageSize:Int, page: Int
  ): Either<Error, List<Article>>
}
