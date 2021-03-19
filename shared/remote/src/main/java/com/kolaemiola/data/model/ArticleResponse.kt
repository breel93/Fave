package com.kolaemiola.data.model

import com.squareup.moshi.Json

//@JsonClass(generateAdapter = true)
data class ArticleResponse(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "totalResults") val totalResults: Long,
    @field:Json(name = "articles") val articles: List<ArticleRemoteModel>
)
