package com.fave.breezil.fave.db


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.fave.breezil.fave.R
import com.fave.breezil.fave.model.Articles
import com.fave.breezil.fave.model.BookMark

@Database(entities = [BookMark::class, Articles::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookMarkDao(): BookMarkDao
    abstract fun articleDao(): ArticleDao

    companion object {
        private var appDatabase: AppDatabase? = null

        @Synchronized
        fun getAppDatabase(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, context.getString(R.string.article_db))
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return appDatabase as AppDatabase
        }
    }
}
