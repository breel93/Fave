package com.example.data.remote

import arrow.core.Either
import com.example.core.model.Error
import com.example.core.model.Source

interface SourcesRemote {
  suspend fun getSources(
      category: String, language: String, country: String
  ): Either<Error, List<Source>>
}

