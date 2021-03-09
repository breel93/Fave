package com.kolaemiola.local.model

import com.squareup.moshi.Json

data class ArticleResponse(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "totalResults") val totalResults: Long,
    @field:Json(name = "message") val message: String,
    @field:Json(name = "articles") val articles: List<ArticleRemoteModel>
)
