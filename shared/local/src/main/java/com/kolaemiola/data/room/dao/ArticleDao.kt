package com.kolaemiola.data.room.dao

import androidx.room.*
import com.kolaemiola.data.entities.ArticleEntity


@Dao
interface ArticleDao {
  @Query("SELECT * FROM article ORDER BY id DESC")
  suspend fun getArticles(): List<ArticleEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(article: ArticleEntity)

  @Delete
  suspend fun delete(article: ArticleEntity)

  @Query("DELETE FROM article")
  suspend fun deleteAll()
}