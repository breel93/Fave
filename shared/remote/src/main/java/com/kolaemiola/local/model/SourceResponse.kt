package com.kolaemiola.local.model

import com.squareup.moshi.Json

internal data class SourceResponse(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "sources") val sources: List<SourceRemoteModel>
)