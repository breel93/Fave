package com.kolaemiola.remote.api

import com.kolaemiola.remote.model.ArticleResponse
import com.kolaemiola.remote.model.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
  @GET("/v2/sources")
  suspend fun getSources(
      @Query("category") category: String? = null,
      @Query("language") language: String? = null,
      @Query("country") country: String? = null,
      @Query("apiKey") apiKey: String
  ):Response<SourceResponse>

  @GET("/v2/everything")
  suspend fun getEverything(
      @Query("q") query: String,
      @Query("sources") sources: String? = null,
      @Query("sortBy") sortBy: String? = null,
      @Query("from") from: String? = null,
      @Query("to") to: String? = null,
      @Query("language") language: String? = null,
      @Query("pageSize") pageSize: Int,
      @Query("page") page: Int,
      @Query("apiKey") apiKey: String
  ):Response<ArticleResponse>

  @GET("/v2/top-headlines")
  suspend fun getHeadLines(
      @Query("country") country: String? = null,
      @Query("sources") sources: String? = null,
      @Query("category") category: String? = null,
      @Query("q") query: String? = null,
      @Query("pageSize") pageSize: Int,
      @Query("page") page: Int,
      @Query("apiKey") apiKey: String
  ):Response<ArticleResponse>

}