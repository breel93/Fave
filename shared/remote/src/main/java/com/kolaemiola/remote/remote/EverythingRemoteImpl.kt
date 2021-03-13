package com.kolaemiola.remote.remote

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import com.example.data.remote.EverythingRemote
import com.kolaemiola.remote.api.NewsAPI
import com.kolaemiola.remote.mapper.ArticleRemoteMapper
import com.kolaemiola.remote.BuildConfig.NEWS_API_KEY
import javax.inject.Inject

class EverythingRemoteImpl @Inject constructor(
  private val newsAPI: NewsAPI,
  private val articleRemoteMapper: ArticleRemoteMapper
): EverythingRemote {
  override suspend fun getEverything(query: String, sources: String, sortBy: String, from: String,
                                     to: String, language: String, pageSize: Int, page: Int):
      Either<Error, List<Article>> {
    val everythingResponse = newsAPI.getEverything(query,sources,sortBy,
        from,to,language,pageSize,page,NEWS_API_KEY)
    return if(everythingResponse.body() != null){
      Either.Right(articleRemoteMapper.mapToDataList(everythingResponse.body()!!.articles))
    }else{
      Either.Left(Error(message = "Oops, Something is not working right"))
    }
  }

}