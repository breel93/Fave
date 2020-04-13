package com.fave.breezil.fave.api

import com.fave.breezil.fave.model.ArticleResult
import com.fave.breezil.fave.model.SourceResponse
import io.reactivex.Observable

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    fun getHeadline(@Query("country") country: String?,
                    @Query("sources") sources: String?,
                    @Query("category") category: String?,
                    @Query("q") query: String?,
                    @Query("pageSize") pageSize: Int,
                    @Query("page") page: Int,
                    @Query("apiKey") apiKey: String): Single<ArticleResult>


    @GET("/v2/top-headlines")
    fun getHeadlines(@Query("country") country: String?,
                     @Query("sources") sources: String?,
                     @Query("category") category: String?,
                     @Query("q") query: String?,
                     @Query("pageSize") pageSize: Int,
                     @Query("page") page: Int,
                     @Query("apiKey") apiKey: String): Observable<ArticleResult>



    @GET("/v2/everything")
    fun getEverything(@Query("q") query: String,
                       @Query("sources") sources: String,
                       @Query("sortBy") sortBy: String?,
                       @Query("from") from: String?,
                       @Query("to") to: String?,
                       @Query("language") language: String?,
                       @Query("pageSize") pageSize: Int,
                       @Query("page") page: Int,
                       @Query("apiKey") apiKey: String): Single<ArticleResult>

    @GET("/v2/everything")
    fun getBreakingNews(@Query("q") query: String,
                        @Query("sources") sources: String,
                        @Query("sortBy") sortBy: String?,
                        @Query("from") from: String?,
                        @Query("to") to: String?,
                        @Query("language") language: String?,
                        @Query("pageSize") pageSize: Int,
                        @Query("page") page: Int,
                        @Query("apiKey") apiKey: String) : Observable<ArticleResult>

    @GET("/v2/sources")
    fun getSources(@Query("category") category: String?,
                   @Query("language") language: String?,
                   @Query("country") country: String?,
                   @Query("apiKey") apiKey: String): Observable<SourceResponse>

}
