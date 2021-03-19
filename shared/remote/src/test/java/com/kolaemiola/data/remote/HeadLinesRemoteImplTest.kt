package com.kolaemiola.data.remote

import arrow.core.orNull
import com.example.data.remote.HeadLinesRemote
import com.kolaemiola.data.api.NewsAPI
import com.kolaemiola.data.mapper.ArticleRemoteMapper
import com.kolaemiola.data.utils.MockData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class HeadLinesRemoteImplTest{
  private val newsAPI: NewsAPI = mock()
  private val articleMapper = ArticleRemoteMapper()
  private lateinit var headLinesRemote: HeadLinesRemote

  @Before
  fun setup(){
    headLinesRemote = HeadLinesRemoteImpl(newsAPI = newsAPI, articleRemoteMapper = articleMapper)
  }

  @Test
  fun `should get headlines from newAPI successfully`() = runBlocking {
    //given that the newAPI return articlesList
    whenever(newsAPI.getHeadLines(country = "", sources = "",category = "business", query = "",
      pageSize = 5, page = 1)).thenReturn(
        Response.success(MockData.mockArticleResponse)
      )

    //when we call the everything remote to get article list
    val articles = headLinesRemote.getHeadLines(category = "business", sources = "", country = "",
      query = "", pageSize = 5, page = 1).orNull()

    //then assert that we return articles list
    assertThat(articles).isNotEmpty()
    assertThat(MockData.mockArticles.size).isEqualTo(articles?.size)

  }

  @Test
  fun `should return correct articles`() = runBlocking {
    //given that the newsAPI returns articlesList
    whenever(newsAPI.getHeadLines(category = "business", sources = "", country = "",
      query = "", pageSize = 5, page = 1)).thenReturn(
      Response.success(
        MockData.mockArticleResponse
      )
    )

    //when we call the everything remote to get article list
    val articles = headLinesRemote.getHeadLines(category = "business", sources = "", country = "",
      query = "", pageSize = 5, page = 1).orNull()
    val article0 = articles?.first()
    val article1 = articles?.last()


    //then assert that we return correct article items
    assertThat(MockData.mockArticle0.title).isEqualTo(article0!!.title)
    assertThat(MockData.mockArticle0.urlToImage).isEqualTo(article0.urlToImage)
    assertThat(MockData.mockArticle0.url).isEqualTo(article0.url)
    assertThat(MockData.mockArticle0.content).isEqualTo(article0.content)
    assertThat(MockData.mockArticle0.description).isEqualTo(article0.description)
    assertThat(MockData.mockArticle0.publishedAt).isEqualTo(article0.publishedAt)

    assertThat(MockData.mockArticle3.title).isEqualTo(article1!!.title)
    assertThat(MockData.mockArticle3.urlToImage).isEqualTo(article1.urlToImage)
    assertThat(MockData.mockArticle3.url).isEqualTo(article1.url)
    assertThat(MockData.mockArticle3.content).isEqualTo(article1.content)
    assertThat(MockData.mockArticle3.description).isEqualTo(article1.description)
    assertThat(MockData.mockArticle3.publishedAt).isEqualTo(article1.publishedAt)
  }

  @Test
  fun `should return error when endpoint returns error`() = runBlocking {
    //given that api calls getEverything
    whenever(newsAPI.getHeadLines(category = "", sources = "", country = "",
      query = "", pageSize = 5, page = 1)).thenReturn(
      Response.error(401,
        "".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
      )
    )
    //when we call the everything remote to get article list
    val articles =  headLinesRemote.getHeadLines(category = "", sources = "", country = "",
      query = "", pageSize = 5, page = 1)

    // then it return error
    assertThat(articles.isLeft()).isTrue()
  }
}