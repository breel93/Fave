package com.kolaemiola.data.model

import com.squareup.moshi.Json

data class SourceResponse(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "sources") val sources: List<SourceRemoteModel>
)
