package com.kolaemiola.data.remote

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import com.example.data.remote.EverythingRemote
import com.kolaemiola.data.api.NewsAPI
import com.kolaemiola.data.mapper.ArticleRemoteMapper
import javax.inject.Inject

class EverythingRemoteImpl @Inject constructor(
  private val newsAPI: NewsAPI,
  private val articleRemoteMapper: ArticleRemoteMapper
): EverythingRemote {
  override suspend fun getEverything(query: String, sources: String, sortBy: String, from: String,
                                     to: String, language: String, pageSize: Int, page: Int):
      Either<Error, List<Article>> {
    val everythingResponse = newsAPI.getEverything(query = query, sources = sources, sortBy = sortBy,
        from = from,to = to,language = language,pageSize = pageSize,page=page)
    return if(everythingResponse.body() != null){
      Either.Right(articleRemoteMapper.mapToDataList(everythingResponse.body()!!.articles))
    }else{
      Either.Left(Error(message = "Oops, Something is not working right"))
    }
  }

}