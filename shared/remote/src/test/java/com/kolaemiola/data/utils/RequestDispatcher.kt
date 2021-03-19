package com.kolaemiola.data.utils

import com.kolaemiola.data.BuildConfig.NEWS_API_KEY
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

class RequestDispatcher {

  val successDispatcher = object : Dispatcher(){
    override fun dispatch(request: RecordedRequest): MockResponse {
      return when (request.path) {
        "/v2/top-headlines?pageSize=5&page=1&apiKey=$NEWS_API_KEY" -> createSuccessResponseFromFile("article_response.json")
        "/v2/everything?q=&pageSize=5&page=1&apiKey=$NEWS_API_KEY" -> createSuccessResponseFromFile("article_response.json")
        "/v2/sources?apiKey=$NEWS_API_KEY" -> createSuccessResponseFromFile("sources_response.json")
        else -> throw IllegalArgumentException("Unknown Request Path ${request.path}")
      }
    }
  }

  val errorDispatcher = object : Dispatcher(){
    override fun dispatch(request: RecordedRequest): MockResponse {
      return createErrorResponseFromFile(file = "error.json")
    }
  }

  private fun createSuccessResponseFromFile(file: String): MockResponse{
    return createResponseFile(file = file, response = MockResponse())
  }

  private fun createErrorResponseFromFile(file: String): MockResponse{
    val mockResponse = MockResponse()
    mockResponse.setResponseCode(400)
    return createResponseFile(file, mockResponse)
  }
  private fun createResponseFile(file: String, response: MockResponse): MockResponse{
    val inputStream = javaClass.classLoader
      .getResourceAsStream("api-response/$file")
    val source = inputStream.source().buffer()
    response.setBody(source.readString(StandardCharsets.UTF_8))
    return response
  }
  companion object{

  }

}