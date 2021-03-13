package com.kolaemiola.local.remote

import arrow.core.Either
import com.example.core.model.Error
import com.example.core.model.Source
import com.example.data.remote.SourcesRemote
import com.kolaemiola.local.api.NewsAPI
import com.kolaemiola.local.mapper.SourceRemoteMapper
import com.kolaemiola.remote.BuildConfig.NEWS_API_KEY
import javax.inject.Inject

class SourceRemoteImpl @Inject constructor(
    private val newsAPI: NewsAPI,
    private val sourceRemoteMapper: SourceRemoteMapper
): SourcesRemote{
  override suspend fun getSources(category: String, language: String, country: String): Either<Error, List<Source>> {
    val sourcesResponse = newsAPI.getSources(category,language,country, NEWS_API_KEY)

    return if(sourcesResponse.body() != null){
      Either.Right(sourceRemoteMapper.mapToDataList(sourcesResponse.body()!!.sources))
    }else{
      Either.Left(Error(message = "Oops, Something is not working right"))
    }
  }
}