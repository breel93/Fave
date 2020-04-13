package com.fave.breezil.fave.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

import com.fave.breezil.fave.model.Articles


@Dao
interface ArticleDao {

    @Transaction
    @Query("SELECT * FROM article_table ORDER BY id ASC")
    fun pagedArticle(): DataSource.Factory<Int, Articles>

    @Insert
    fun insert(articles: Articles)

    @Query("DELETE FROM article_table")
    fun deleteAllArticle()
}
