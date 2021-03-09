package com.kolaemiola.local.api

import com.kolaemiola.local.api.interceptor.NoInternetInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiServiceCreator {

  internal fun createApiService(isDebug: Boolean, moshi: Moshi, baseUrl: String): NewsAPI {
    val okHttpClient: OkHttpClient = makeOkHttpClient(makeLoggingInterceptor((isDebug)))
    return makeApiService(
      okHttpClient,
      moshi,
      baseUrl
    )
  }

  private fun makeApiService(okHttpClient: OkHttpClient, moshi: Moshi, baseUrl: String): NewsAPI {
    val retrofit: Retrofit = Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
    return retrofit.create(NewsAPI::class.java)
  }

  private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(NoInternetInterceptor)
      .addInterceptor(httpLoggingInterceptor)
      .connectTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .build()
  }

  private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (isDebug) {
      HttpLoggingInterceptor.Level.BODY
    } else {
      HttpLoggingInterceptor.Level.NONE
    }
    return logging
  }
}