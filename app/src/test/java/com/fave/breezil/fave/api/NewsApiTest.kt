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
  fun fetchArticleResponseTest(){
    enqueueResponse("article_response.json")
    val articleResult = newsApi!!.getHeadline("us","","technology",
      "",5,1,NEWS_API_KEY).blockingGet()
    Assert.assertEquals("ok",articleResult.status)
    Assert.assertEquals(4,articleResult.totalResults)
    Assert.assertEquals(4,articleResult.articles.size)
    Assert.assertEquals("PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",articleResult.articles[0].title)
  }
}

