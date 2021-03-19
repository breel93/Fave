package com.kolaemiola.remote.utils

import com.kolaemiola.remote.BuildConfig.DEBUG
import com.kolaemiola.remote.api.ApiServiceCreator
import com.kolaemiola.remote.api.NewsAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

internal open class BaseTest {
  lateinit var mockWebServer: MockWebServer
  lateinit var newsAPI: NewsAPI

  @Before
  open fun setup(){
    mockWebServer = MockWebServer()
    mockWebServer.start()
    newsAPI = ApiServiceCreator.createApiService(DEBUG, mockWebServer.url("/").toString())
  }

  @After
  fun stopService(){
    mockWebServer.shutdown()
  }
  private fun buildOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
    return OkHttpClient.Builder()
      .addInterceptor(httpLoggingInterceptor)
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(10, TimeUnit.SECONDS)
      .build()
  }
  private inner class DateAdapter{
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
  }
}

