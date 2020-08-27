package com.fave.breezil.fave.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.BufferedSource
import okio.Source
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets
import kotlin.jvm.Throws

@RunWith(JUnit4::class)
open class ApiAbstract<T> {
  @Rule
  @JvmField
  val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  lateinit var mockWebServer: MockWebServer

  @Before
  @Throws(IOException::class)
  fun mockServer() {
    mockWebServer = MockWebServer()
    mockWebServer.start()
  }

  @After
  @Throws(IOException::class)
  fun stopServer() {
    mockWebServer.shutdown()
  }

  @Throws(IOException::class)
  fun enqueueResponse(fileName: String) {
    enqueueResponse(fileName, emptyMap())
  }

  @Throws(IOException::class)
  private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
    val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
    val source = inputStream.source().buffer()
    val mockResponse = MockResponse()
    for ((key, value) in headers) {
      mockResponse.addHeader(key, value)
    }
    mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
  }

  fun createService(clazz: Class<T>): T {
    return Retrofit.Builder()
      .baseUrl(mockWebServer.url("/"))
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(clazz)
  }

  @Throws(InterruptedException::class)
  fun assertRequestPath(path: String?) {
    val request = mockWebServer.takeRequest()
    MatcherAssert.assertThat(request.path, CoreMatchers.`is`(path))
  }
}


