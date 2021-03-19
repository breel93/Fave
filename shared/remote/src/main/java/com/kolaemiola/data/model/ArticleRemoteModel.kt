package com.kolaemiola.data.model

import com.squareup.moshi.Json
import java.util.Date

//@JsonClass(generateAdapter = true)
data class ArticleRemoteModel(
    @field:Json(name = "title") val title: String,
    @field:Json(name = "author") val author: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "urlToImage") val urlToImage: String?,
    @field:Json(name = "publishedAt") val publishedAt: Date,
    @field:Json(name = "content") val content: String?,
    @field:Json(name = "source") val source: ArticleSourceRemoteModel?
)
