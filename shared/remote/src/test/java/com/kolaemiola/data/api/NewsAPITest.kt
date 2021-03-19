package com.kolaemiola.data.api

import com.kolaemiola.data.utils.BaseTest
import com.kolaemiola.data.utils.RequestDispatcher
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import com.kolaemiola.data.BuildConfig.NEWS_API_KEY
import com.kolaemiola.data.utils.MockData
import org.junit.Test

internal class NewsAPITest : BaseTest(){
  @Test
  fun `should return headlines list successfully`() = runBlocking {
    //given that the request completes successfully
    mockWebServer.dispatcher = RequestDispatcher().successDispatcher

    //when we call get_headlines get called
    val articlesResponse = newsAPI.getHeadLines(pageSize = 5, page = 1).body()

    //then verify that response as it is in json file
    assertThat(MockData.mockArticleResponse.totalResults).isEqualTo(articlesResponse?.totalResults)
    assertThat(articlesResponse?.articles).isNotEmpty()
    assertThat(MockData.mockArticleResponse.articles.size).isEqualTo(articlesResponse?.articles?.size)
    assertThat(MockData.mockArticle0).isEqualTo(articlesResponse?.articles?.get(0))
    assertThat(MockData.mockArticle2).isEqualTo(articlesResponse?.articles?.get(2))
  }

  @Test
  fun `should return everything list successfully`() = runBlocking {
    //given that the request complete successfully
    mockWebServer.dispatcher = RequestDispatcher().successDispatcher

    //when we call get_everything get called
    val articlesResponse = newsAPI.getEverything(query = "", pageSize = 5, page = 1).body()

    //then verify that response as it is in json file
    assertThat(MockData.mockArticleResponse.totalResults).isEqualTo(articlesResponse?.totalResults)
    assertThat(articlesResponse?.articles).isNotEmpty()
    assertThat(MockData.mockArticleResponse.articles.size).isEqualTo(articlesResponse?.articles?.size)
    assertThat(MockData.mockArticle0).isEqualTo(articlesResponse?.articles?.get(0))
    assertThat(MockData.mockArticle2).isEqualTo(articlesResponse?.articles?.get(2))
  }

  @Test
  fun `should return sources list successfully`() = runBlocking {
    //given that the request complete successfully
    mockWebServer.dispatcher = RequestDispatcher().successDispatcher

    //when we call get_everything get called
    val sourcesResponse = newsAPI.getSources().body()

    //then verify that response as it is in json file
    assertThat(MockData.mockSourceResponse.status).isEqualTo(sourcesResponse?.status)
    assertThat(sourcesResponse?.sources).isNotEmpty()
    assertThat(MockData.mockSourceResponse.sources.size).isEqualTo(sourcesResponse?.sources?.size)
    assertThat(MockData.mockSource0).isEqualTo(sourcesResponse?.sources?.get(0))
    assertThat(MockData.mockSource2).isEqualTo(sourcesResponse?.sources?.get(2))
  }

  @Test
  fun `should return error when call fails`() = runBlocking {
    //given that the request complete successfully
    mockWebServer.dispatcher = RequestDispatcher().errorDispatcher

    //when we call get_headlines get called
    val articlesResponse = newsAPI.getHeadLines(pageSize = 5, page = 1)

    //then verify that error  response as it is in error json file
    assertThat(articlesResponse.body()).isNull()
    assertThat(articlesResponse.errorBody()).isNotNull()
  }

  @Test
  fun `should call the correct headline path`() = runBlocking {
    //given that the request complete successfully
    mockWebServer.dispatcher = RequestDispatcher().successDispatcher

    //when we call headline path
    newsAPI.getHeadLines(pageSize = 5, page = 1)

    //then verify url path is correct
    assertThat("GET /v2/top-headlines?pageSize=5&page=1&apiKey=$NEWS_API_KEY HTTP/1.1")
      .isEqualTo(mockWebServer.takeRequest().requestLine)
  }

  @Test
  fun `should call the correct everything path`() = runBlocking {
    //given that the request complete successfully
    mockWebServer.dispatcher = RequestDispatcher().successDispatcher

    //when we call everything path
    newsAPI.getEverything(query = "", pageSize = 5, page = 1)

    //then verify url path is correct
    assertThat("GET /v2/everything?q=&pageSize=5&page=1&apiKey=$NEWS_API_KEY HTTP/1.1")
      .isEqualTo(mockWebServer.takeRequest().requestLine)
  }
  @Test
  fun `should call the correct sources path`() = runBlocking {
    //given that the request complete successfully
    mockWebServer.dispatcher = RequestDispatcher().successDispatcher

    //when we call sources path
    newsAPI.getSources()

    //then verify url path is correct
    assertThat("GET /v2/sources?apiKey=$NEWS_API_KEY HTTP/1.1")
      .isEqualTo(mockWebServer.takeRequest().requestLine)
  }


}