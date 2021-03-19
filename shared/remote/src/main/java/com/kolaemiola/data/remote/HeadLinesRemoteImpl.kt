package com.kolaemiola.data.remote

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import com.example.data.remote.HeadLinesRemote
import com.kolaemiola.data.api.NewsAPI
import com.kolaemiola.data.mapper.ArticleRemoteMapper
import javax.inject.Inject

class HeadLinesRemoteImpl @Inject constructor(
  private val newsAPI: NewsAPI,
  private val articleRemoteMapper: ArticleRemoteMapper
): HeadLinesRemote{
  override suspend fun getHeadLines(country: String, sources: String, category: String?,
                                    query: String, pageSize: Int, page: Int): Either<Error, List<Article>> {
    val articleResponse = newsAPI.getHeadLines(country,sources,category,query,pageSize,page)
    return if(articleResponse.body() != null){
      Either.Right(articleRemoteMapper.mapToDataList(articleResponse.body()!!.articles))
    }else{
      Either.Left(Error(message = "Oops, Something is not working right"))
    }
  }

}