package com.kolaemiola.local.room.base

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.kolaemiola.local.room.dao.ArticleDao
import com.kolaemiola.local.room.db.ArticleDatabase
import org.junit.After
import org.junit.Before

internal open class AppDatabaseTest {
  protected lateinit var db: ArticleDatabase
  protected lateinit var articleDao: ArticleDao

  @Before
  fun setup(){
    db = Room.inMemoryDatabaseBuilder(
      ApplicationProvider.getApplicationContext(),
      ArticleDatabase::class.java).allowMainThreadQueries().build()
    articleDao = db.articleDao()
  }
  @After
  fun closeDb() {
    db.close()
  }
}