package com.fave.breezil.fave.api

import com.fave.breezil.fave.util.ApiAbstract
import com.fave.breezil.fave.util.MainCoroutinesRule
import com.fave.breezil.fave.utils.Constant.Companion.NEWS_API_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
class NewsApiTest : ApiAbstract<NewsApi>(){
  private lateinit var newsApi: NewsApi

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @Before
  fun initService(){
    this.newsApi = createService(NewsApi::class.java)
  }

  @Test
  fun requestResponseTest() = runBlocking {
    enqueueResponse("article_response.json")
    val resultResponse = newsApi.getHeadLines("us","","technology",
      "",5,1,NEWS_API_KEY).body()
    val request = mockWebServer.takeRequest()
    Assert.assertNotNull(resultResponse)
    assertThat(request.path, `is`("/v2/top-headlines"))
  }

  @Throws(IOException::class)
  @Test
  fun getHeadlineArticleResponseTest() = runBlocking{
    enqueueResponse("article_response.json")
    val articleResult = newsApi.getHeadLines("us","","technology",
      "",5,1,NEWS_API_KEY).body()
    Assert.assertEquals(articleResult!!.status,"ok")
    Assert.assertEquals(articleResult.totalResults,4)
    Assert.assertEquals(articleResult.articles.size,4)
    Assert.assertEquals(articleResult.articles[0].title,"PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",
      )
  }

  @Throws(IOException::class)
  @Test
  fun getEverythingArticleResponseTest() = runBlocking{
    enqueueResponse("article_response.json")
    val articleResult = newsApi.getEverything("","","","" +
        "","","",5,1,NEWS_API_KEY).body()
    Assert.assertEquals(articleResult!!.status,"ok")
    Assert.assertEquals(articleResult.totalResults,4)
    Assert.assertEquals(articleResult.articles.size,4)
    Assert.assertEquals(articleResult.articles[0].title,"PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",)
  }



  @Throws(IOException::class)
  @Test
  fun getSourcesResponseTest() = runBlocking{
    enqueueResponse("sources_response.json")
    val sourceResult = newsApi.getSources("","en","us",NEWS_API_KEY)
    Assert.assertEquals(sourceResult.body()!!.status,"ok")
    Assert.assertEquals(sourceResult.body()!!.sources[1].id,"abc-news-au" )
  }

}

