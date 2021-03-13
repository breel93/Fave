package com.kolaemiola.remote.remote

import arrow.core.Either
import com.example.core.model.Article
import com.example.core.model.Error
import com.example.data.remote.HeadLinesRemote
import com.kolaemiola.remote.api.NewsAPI
import com.kolaemiola.remote.mapper.ArticleRemoteMapper
import com.kolaemiola.remote.BuildConfig.NEWS_API_KEY
import javax.inject.Inject

class HeadLinesRemoteImpl @Inject constructor(
  private val newsAPI: NewsAPI,
  private val articleRemoteMapper: ArticleRemoteMapper
): HeadLinesRemote{
  override suspend fun getHeadLines(country: String, sources: String, category: String?,
                                    query: String, pageSize: Int, page: Int): Either<Error, List<Article>> {
    val articleResponse = newsAPI.getHeadLines(country,sources,category,query,pageSize,page,NEWS_API_KEY)
    return if(articleResponse.body() != null){
      Either.Right(articleRemoteMapper.mapToDataList(articleResponse.body()!!.articles))
    }else{
      Either.Left(Error(message = "Oops, Something is not working right"))
    }
  }

}