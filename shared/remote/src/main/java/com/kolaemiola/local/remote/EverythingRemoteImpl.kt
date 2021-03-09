package com.kolaemiola.local.remote

import com.example.core.model.Article
import com.example.data.remote.EverythingRemote
import com.kolaemiola.local.api.NewsAPI
import com.kolaemiola.local.mapper.ArticleRemoteMapper
import javax.inject.Inject

class EverythingRemoteImpl @Inject constructor(
  private val newsAPI: NewsAPI,
  private val articleRemoteMapper: ArticleRemoteMapper
): EverythingRemote {
  override suspend fun getEverything(query: String, sources: String, sortBy: String, from: String,
                                     to: String, language: String, pageSize: Int, page: Int):
      List<Article> {
    TODO("Not yet implemented")
  }

}