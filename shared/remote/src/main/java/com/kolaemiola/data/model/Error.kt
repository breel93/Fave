package com.kolaemiola.data.model

import com.squareup.moshi.Json

internal data class Error(
    @field:Json(name = "code") val code: String,
    @field:Json(name = "message") val message: String
)