package com.kolaemiola.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleResponse(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "totalResults") val totalResults: Long,
    @field:Json(name = "articles") val articles: List<ArticleRemoteModel>
)
