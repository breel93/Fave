package com.kolaemiola.remote.di


import com.example.data.remote.EverythingRemote
import com.example.data.remote.HeadLinesRemote
import com.example.data.remote.SourcesRemote
import com.kolaemiola.remote.api.ApiServiceCreator
import com.kolaemiola.remote.api.NewsAPI
import com.kolaemiola.remote.remote.EverythingRemoteImpl
import com.kolaemiola.remote.remote.HeadLinesRemoteImpl
import com.kolaemiola.remote.remote.SourceRemoteImpl
import com.kolaemiola.remote.BuildConfig.BASE_URL
import com.kolaemiola.remote.BuildConfig.DEBUG
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface RemoteModule {

  @get:Binds
  val HeadLinesRemoteImpl.headlineRemote: HeadLinesRemote

  @get:Binds
  val EverythingRemoteImpl.everythingRemote: EverythingRemote

  @get:Binds
  val SourceRemoteImpl.sourceRemote: SourcesRemote

  companion object {
//    @get:[Provides Singleton]
//    val provideMoshi: Moshi
//      get() = Moshi.Builder()
//          .add(KotlinJsonAdapterFactory()).build()

    @[Provides Singleton]
    internal fun provideApiService(): NewsAPI =
        ApiServiceCreator.createApiService(DEBUG, BASE_URL)
  }
}

