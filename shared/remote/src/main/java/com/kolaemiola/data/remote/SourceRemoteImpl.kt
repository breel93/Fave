package com.kolaemiola.data.remote

import arrow.core.Either
import com.example.core.model.Error
import com.example.core.model.Source
import com.example.data.remote.SourcesRemote
import com.kolaemiola.data.api.NewsAPI
import com.kolaemiola.data.mapper.SourceRemoteMapper
import javax.inject.Inject

class SourceRemoteImpl @Inject constructor(
    private val newsAPI: NewsAPI,
    private val sourceRemoteMapper: SourceRemoteMapper
): SourcesRemote{
  override suspend fun getSources(category: String, language: String, country: String): Either<Error, List<Source>> {
    val sourcesResponse = newsAPI.getSources(category,language,country)

    return if(sourcesResponse.body() != null){
      Either.Right(sourceRemoteMapper.mapToDataList(sourcesResponse.body()!!.sources))
    }else{
      Either.Left(Error(message = "Oops, Something is not working right"))
    }
  }
}