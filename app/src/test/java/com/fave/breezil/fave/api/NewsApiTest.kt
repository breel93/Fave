package com.fave.breezil.fave.api

import com.fave.breezil.fave.util.ApiAbstract
import com.fave.breezil.fave.util.MainCoroutinesRule
import com.fave.breezil.fave.utils.Constant.Companion.NEWS_API_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
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

  @Throws(IOException::class)
  @Test
  fun getHeadlineArticleResponseTest() = runBlocking{
    enqueueResponse("article_response.json")
    val articleResult = newsApi.getHeadLines("us","","technology",
      "",5,1,NEWS_API_KEY)
    mockWebServer.takeRequest()
    Assert.assertEquals("ok",articleResult.body()!!.status)
    Assert.assertEquals(4,articleResult.body()!!.totalResults)
    Assert.assertEquals(4,articleResult.body()!!.articles.size)
    Assert.assertEquals("PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",
      articleResult.body()!!.articles[0].title)
  }

  @Throws(IOException::class)
  @Test
  fun getEverythingArticleResponseTest()= runBlocking{
    enqueueResponse("article_response.json")
    val articleResult = newsApi.getEverything("","","","" +
        "","","",5,1,NEWS_API_KEY)
    mockWebServer.takeRequest()
    Assert.assertEquals("ok",articleResult.body()!!.status)
    Assert.assertEquals(4,articleResult.body()!!.totalResults)
    Assert.assertEquals(4,articleResult.body()!!.articles.size)
    Assert.assertEquals("PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",
      articleResult.body()!!.articles[0].title)
  }



  @Throws(IOException::class)
  @Test
  fun getSourcesResponseTest() = runBlocking{
    enqueueResponse("source_response.json")
    val sourceResult = newsApi.getSources("","en","us",NEWS_API_KEY)
    mockWebServer.takeRequest()
    Assert.assertEquals("ok", sourceResult.body()!!.status)
    Assert.assertEquals("abc-news-au", sourceResult.body()!!.sources[1].id)
  }

}

