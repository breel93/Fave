package com.kolaemiola.remote.api

import com.kolaemiola.remote.api.interceptor.NoInternetInterceptor
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object ApiServiceCreator {

  internal fun createApiService(isDebug: Boolean, baseUrl: String): NewsAPI {
    val okHttpClient: OkHttpClient = makeOkHttpClient(makeLoggingInterceptor((isDebug)))
    val moshi = Moshi.Builder()
      .add(Date::class.java, DateAdapterFactory())
      .add(KotlinJsonAdapterFactory())
      .build()
//      .add(KotlinJsonAdapterFactory()).build()

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

  private class DateAdapterFactory : JsonAdapter<Date>() {
    private val adapter: DateAdapter = DateAdapter()

    override fun fromJson(reader: JsonReader): Date? {
      val dateString = reader.nextString()
      return adapter.parseFromString(dateString)
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
      writer.value(adapter.convertToString(value!!))
    }
  }

  private class DateAdapter {
    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")

    @ToJson
    @Synchronized
    fun parseFromString(string: String): Date = sdf.parse(string)

    @FromJson
    @Synchronized
    fun convertToString(obj: Date): String = sdf.format(obj)
  }

}