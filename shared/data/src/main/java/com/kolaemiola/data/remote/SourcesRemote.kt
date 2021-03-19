package com.kolaemiola.data.remote

import arrow.core.Either
import com.kolaemiola.core.model.Error
import com.kolaemiola.core.model.Source

interface SourcesRemote {
  suspend fun getSources(
      category: String, language: String, country: String
  ): Either<Error, List<Source>>
}

