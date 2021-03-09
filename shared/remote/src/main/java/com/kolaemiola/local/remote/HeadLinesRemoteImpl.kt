package com.kolaemiola.local.remote

import com.example.core.model.Article
import com.example.data.remote.HeadLinesRemote
import com.kolaemiola.local.api.NewsAPI
import com.kolaemiola.local.mapper.ArticleRemoteMapper
import javax.inject.Inject

class HeadLinesRemoteImpl @Inject constructor(
  private val newsAPI: NewsAPI,
  private val articleRemoteMapper: ArticleRemoteMapper
): HeadLinesRemote{
  override suspend fun getHeadLines(country: String, sources: String, category: String?,
                                    query: String, pageSize: Int, page: Int): List<Article> {
    TODO("Not yet implemented")
  }

}