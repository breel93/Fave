/**
 *  Designed and developed by Fave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.fave.breezil.fave.di.module

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fave.breezil.fave.api.NewsApi
import com.fave.breezil.fave.api.OkHttp
import com.fave.breezil.fave.db.AppDatabase
import com.fave.breezil.fave.db.ArticleDao
import com.fave.breezil.fave.utils.Constant.Companion.BASE_URL
import com.fave.breezil.fave.utils.Constant.Companion.FAVE_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
  @Singleton
  @Provides
  internal fun provideNewsApi(): NewsApi {
    val okHttp = OkHttp()
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttp.client)
      .build()
      .create(NewsApi::class.java)
  }

  @Singleton
  @Provides
  internal fun provideDb(app: Application): AppDatabase {
    val migration12: Migration = object : Migration(1, 2) {
      override fun migrate(database: SupportSQLiteDatabase) {
        database.beginTransaction()
      }
    }
    return Room.databaseBuilder(app.applicationContext,
      AppDatabase::class.java, FAVE_DB)
      .addMigrations(migration12)
      .build()
  }

  @Provides
  fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

  @Singleton
  @Provides
  internal fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
    return appDatabase.articleDao()
  }

}
