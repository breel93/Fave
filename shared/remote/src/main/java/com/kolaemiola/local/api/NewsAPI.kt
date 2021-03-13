package com.kolaemiola.local.api

import com.kolaemiola.local.model.ArticleResponse
import com.kolaemiola.local.model.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
  @GET("/v2/sources")
  suspend fun getSources(
      @Query("category") category: String?,
      @Query("language") language: String?,
      @Query("country") country: String?,
      @Query("apiKey") apiKey: String
  ):Response<SourceResponse>

  @GET("/v2/everything")
  suspend fun getEverything(
      @Query("q") query: String,
      @Query("sources") sources: String,
      @Query("sortBy") sortBy: String?,
      @Query("from") from: String?,
      @Query("to") to: String?,
      @Query("language") language: String?,
      @Query("pageSize") pageSize: Int,
      @Query("page") page: Int,
      @Query("apiKey") apiKey: String
  ):Response<ArticleResponse>

  @GET("/v2/top-headlines")
  suspend fun getHeadLines(
      @Query("country") country: String?,
      @Query("sources") sources: String?,
      @Query("category") category: String?,
      @Query("q") query: String?,
      @Query("pageSize") pageSize: Int,
      @Query("page") page: Int,
      @Query("apiKey") apiKey: String
  ):Response<ArticleResponse>

}