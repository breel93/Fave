package com.kolaemiola.data.di


import com.example.data.remote.EverythingRemote
import com.example.data.remote.HeadLinesRemote
import com.example.data.remote.SourcesRemote
import com.kolaemiola.data.api.ApiServiceCreator
import com.kolaemiola.data.api.NewsAPI
import com.kolaemiola.data.remote.EverythingRemoteImpl
import com.kolaemiola.data.remote.HeadLinesRemoteImpl
import com.kolaemiola.data.remote.SourceRemoteImpl
import com.kolaemiola.data.BuildConfig.BASE_URL
import com.kolaemiola.data.BuildConfig.DEBUG
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

