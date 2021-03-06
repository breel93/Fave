/**
 *  Designed and developed by Fave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.fave.breezil.fave.api

import com.fave.breezil.fave.model.ArticleResult
import com.fave.breezil.fave.model.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
  @GET("/v2/sources")
  suspend fun getSources(
    @Query("category") category: String?,
    @Query("language") language: String?,
    @Query("country") country: String?,
    @Query("apiKey") apiKey: String
  ): Response<SourceResponse>

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
  ): Response<ArticleResult>

  @GET("/v2/top-headlines")
  suspend fun getHeadLines(
    @Query("country") country: String?,
    @Query("sources") sources: String?,
    @Query("category") category: String?,
    @Query("q") query: String?,
    @Query("pageSize") pageSize: Int,
    @Query("page") page: Int,
    @Query("apiKey") apiKey: String
  ): Response<ArticleResult>

}
