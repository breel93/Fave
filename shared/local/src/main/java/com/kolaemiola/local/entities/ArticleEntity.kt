package com.kolaemiola.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "article")
data class ArticleEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    val title: String,
    val author: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date,
    val content: String?,
    val source: String?,
)
