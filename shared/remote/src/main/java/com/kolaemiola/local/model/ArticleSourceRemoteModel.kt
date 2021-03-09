package com.kolaemiola.local.model

import com.squareup.moshi.Json

data class ArticleSourceRemoteModel(
    @field:Json(name = "name") val name: String
)