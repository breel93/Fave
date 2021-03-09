package com.kolaemiola.local.di

import android.content.Context
import com.kolaemiola.local.room.dao.ArticleDao
import com.kolaemiola.local.room.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface LocalModule {

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