package com.kolaemiola.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kolaemiola.data.entities.ArticleEntity
import com.kolaemiola.data.entities.DateConverter
import com.kolaemiola.data.room.dao.ArticleDao


@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ArticleDatabase : RoomDatabase() {
  abstract fun articleDao(): ArticleDao

  companion object {
    private const val DATABASE_NAME: String = "favnew.db"
    private val migration12: Migration = object : Migration(1, 2) {
      override fun migrate(database: SupportSQLiteDatabase) {
        database.beginTransaction()
      }
    }
    fun build(context: Context): ArticleDatabase = Room.databaseBuilder(
        context.applicationContext,
        ArticleDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration12).fallbackToDestructiveMigration().build()
  }


}

