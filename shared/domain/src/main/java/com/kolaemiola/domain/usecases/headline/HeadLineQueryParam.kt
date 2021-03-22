package com.kolaemiola.domain.usecases.headline

data class HeadLineQueryParam(
  val country: String,
  val sources: String,
  val category: String?,
  val query: String,
  val pageSize: Int,
  val page: Int
)