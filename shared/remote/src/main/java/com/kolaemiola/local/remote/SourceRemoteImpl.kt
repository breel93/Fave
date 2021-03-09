package com.kolaemiola.local.remote

import com.example.core.model.Source
import com.example.data.remote.SourcesRemote
import com.kolaemiola.local.api.NewsAPI
import com.kolaemiola.local.mapper.ArticleRemoteMapper
import javax.inject.Inject

class SourceRemoteImpl @Inject constructor(
    private val newsAPI: NewsAPI,
    private val articleRemoteMapper: ArticleRemoteMapper
): SourcesRemote{
  override suspend fun getSource(category: String, language: String, country: String): List<Source> {
    TODO("Not yet implemented")
  }

}