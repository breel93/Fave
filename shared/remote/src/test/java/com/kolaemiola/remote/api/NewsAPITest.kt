package com.kolaemiola.remote.api

import com.kolaemiola.remote.utils.BaseTest
import com.kolaemiola.remote.utils.RequestDispatcher
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import com.kolaemiola.remote.utils.MockData
import org.junit.Test

internal class NewsAPITest : BaseTest(){
  @Test
  fun `should return headlines list successfully`() = runBlocking {
    //given that the request completes successfully
    mockWebServer.dispatcher = RequestDispatcher().successDispatcher

    //when we call get_headlines get called
    val articlesResponse = newsAPI.getHeadLines(pageSize = 5, page = 1, apiKey = "key").body()

    //then verify that response as it is in json file
    assertThat(MockData.mockArticleResponse.totalResults).isEqualTo(articlesResponse?.totalResults)
  }
}