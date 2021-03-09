package com.kolaemiola.local.di


import com.kolaemiola.local.api.ApiServiceCreator
import com.kolaemiola.local.api.NewsAPI
import com.kolaemiola.remote.BuildConfig.BASE_URL
import com.kolaemiola.remote.BuildConfig.DEBUG
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface RemoteModule {
  companion object {

    @get:[Provides Singleton]
    val provideMoshi: Moshi
      get() = Moshi.Builder()
          .add(KotlinJsonAdapterFactory()).build()

    @[Provides Singleton]
    internal fun provideApiService(moshi: Moshi): NewsAPI =
        ApiServiceCreator.createApiService(DEBUG, moshi, BASE_URL)
  }
}

