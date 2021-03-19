package com.kolaemiola.data.model

import com.squareup.moshi.Json

data class ArticleSourceRemoteModel(
    @field:Json(name = "name") val name: String
)