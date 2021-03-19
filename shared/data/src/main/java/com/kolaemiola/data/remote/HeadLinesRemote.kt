package com.kolaemiola.data.remote

import arrow.core.Either
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.Error

interface HeadLinesRemote {
  suspend fun getHeadLines(
      country: String, sources: String, category: String?,
      query: String, pageSize:Int, page: Int
  ): Either<Error, List<Article>>
}
