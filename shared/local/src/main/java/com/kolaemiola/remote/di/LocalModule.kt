package com.kolaemiola.remote.di

import android.content.Context
import com.example.data.local.BookMarkLocal
import com.kolaemiola.remote.local.BookMarkLocalImpl
import com.kolaemiola.remote.room.dao.ArticleDao
import com.kolaemiola.remote.room.db.ArticleDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface LocalModule {

  @get:Binds
  val BookMarkLocalImpl.bookMarkLocal: BookMarkLocal

  companion object{
    @[Provides Singleton]
    fun provideArticleDatabase(@ApplicationContext context: Context): ArticleDatabase {
      return ArticleDatabase.build(context)
    }

    @[Provides Singleton]
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
      return articleDatabase.articleDao()
    }
  }

}