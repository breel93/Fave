package com.kolaemiola.domain.usecases.sources

data class SourceQueryParam(
  val category: String,
  val language: String,
  val country: String
)