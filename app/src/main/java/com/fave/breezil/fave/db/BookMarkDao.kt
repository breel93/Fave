package com.fave.breezil.fave.db

import androidx.lifecycle.LiveData
import androidx.room.*

import com.fave.breezil.fave.model.BookMark

@Dao
interface BookMarkDao {

    @get:Transaction
    @get:Query("SELECT * FROM bookmark_table ORDER BY id DESC")
    val allBookMarks: LiveData<List<BookMark>>

    @Insert
    fun insert(bookMark: BookMark)

    @Delete
    fun delete(bookMark: BookMark)

    @Query("DELETE FROM bookmark_table WHERE id = :bookMarkId")
    fun deleteArticle(bookMarkId: String)

    @Query("DELETE FROM bookmark_table")
    fun deleteAllBookMarks()

    @Query("SELECT * FROM bookmark_table WHERE id = :bookMarkId")
    fun getMovieById(bookMarkId: Int): LiveData<BookMark>


}
