package com.kolaemiola.core.model

import java.util.Date

data class Article(
    val title: String,
    val author: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
    val content: String,
    val source: String,
)