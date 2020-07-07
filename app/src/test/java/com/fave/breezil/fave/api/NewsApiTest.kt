package com.fave.breezil.fave.api

import com.fave.breezil.fave.util.ApiAbstract
import com.fave.breezil.fave.utils.Constant.Companion.NEWS_API_KEY
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NewsApiTest : ApiAbstract<NewsApi>(){
  private var newsApi: NewsApi? = null

  @Before
  fun initService(){
    this.newsApi = createService(NewsApi::class.java)
  }

  @Test
  fun getHeadlineArticleResponseTest(){
    enqueueResponse("article_response.json")
    val articleResult = newsApi!!.getHeadline("us","","technology",
      "",5,1,NEWS_API_KEY).blockingGet()
    Assert.assertEquals("ok",articleResult.status)
    Assert.assertEquals(4,articleResult.totalResults)
    Assert.assertEquals(4,articleResult.articles.size)
    Assert.assertEquals("PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",articleResult.articles[0].title)
  }

  @Test
  fun getHeadlinesArticleResponseTest(){
    enqueueResponse("article_response.json")
    val articleResult = newsApi!!.getHeadlines("us","","technology",
      "",5,1,NEWS_API_KEY).blockingFirst()
    Assert.assertEquals("ok",articleResult.status)
    Assert.assertEquals(4,articleResult.totalResults)
    Assert.assertEquals(4,articleResult.articles.size)
    Assert.assertEquals("PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",articleResult.articles[0].title)
  }
  @Test
  fun getEverythingArticleResponseTest(){
    enqueueResponse("article_response.json")
    val articleResult = newsApi!!.getEverything("","","","" +
        "","","",5,1,NEWS_API_KEY).blockingGet()
    Assert.assertEquals("ok",articleResult.status)
    Assert.assertEquals(4,articleResult.totalResults)
    Assert.assertEquals(4,articleResult.articles.size)
    Assert.assertEquals("PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",articleResult.articles[0].title)
  }

  @Test
  fun getBreakingNewsArticleResponseTest(){
    enqueueResponse("article_response.json")
    val articleResult = newsApi!!.getBreakingNews("","","","" +
        "","","",5,1,NEWS_API_KEY).blockingFirst()
    Assert.assertEquals("ok",articleResult.status)
    Assert.assertEquals(4,articleResult.totalResults)
    Assert.assertEquals(4,articleResult.articles.size)
    Assert.assertEquals("PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",articleResult.articles[0].title)
  }

  @Test
  fun getSourcesResponseTest(){
    enqueueResponse("source_response.json")
    val sourceResult = newsApi!!.getSources("","en","us",NEWS_API_KEY).blockingFirst()
    Assert.assertEquals("ok", sourceResult.status)
    Assert.assertEquals("abc-news-au", sourceResult.sources[1].id)
  }

}

