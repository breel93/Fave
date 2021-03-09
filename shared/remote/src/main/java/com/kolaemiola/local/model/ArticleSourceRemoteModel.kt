package com.kolaemiola.local.model

import com.squareup.moshi.Json

internal data class ArticleSourceRemoteModel(
    @field:Json(name = "name") val name: String
)