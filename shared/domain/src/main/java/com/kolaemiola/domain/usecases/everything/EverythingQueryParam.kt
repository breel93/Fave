package com.kolaemiola.domain.usecases.everything

data class EverythingQueryParam(
    val query: String,
    val sources: List<String>,
    val sortBy: String,
    val from: String,
    val to: String,
    val language: String,
    val pageSize: Int,
    val page: Int
)