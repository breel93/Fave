package com.kolaemiola.data.remote

import arrow.core.orNull
import com.example.data.remote.SourcesRemote
import com.google.common.truth.Truth.assertThat
import com.kolaemiola.data.api.NewsAPI
import com.kolaemiola.data.mapper.SourceRemoteMapper
import com.kolaemiola.data.utils.MockData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SourceRemoteImplTest {
  private val newsAPI: NewsAPI = mock()
  private val sourceRemoteMapper = SourceRemoteMapper()
  private lateinit var sourcesRemote: SourcesRemote

  @Before
  fun setup() {
    sourcesRemote = SourceRemoteImpl(newsAPI = newsAPI, sourceRemoteMapper = sourceRemoteMapper)
  }

  @Test
  fun `should get sources from newAPI successfully`() = runBlocking {
    //given that the newsAPI returns sourcesList
    whenever(newsAPI.getSources(language = "", category = "", country = "")).thenReturn(
      Response.success(
        MockData.mockSourceResponse
      )
    )

    //when we call the sources remote to get sources list
    val sources = sourcesRemote.getSources(language = "", category = "", country = "").orNull()

    //then assert that we return articles list
    assertThat(sources).isNotEmpty()
    assertThat(MockData.mockArticles.size).isEqualTo(sources?.size)
  }


  @Test
  fun `should return correct sources`() = runBlocking {
    //given that the newsAPI returns sourcesList
    whenever(newsAPI.getSources(language = "", category = "", country = "")).thenReturn(
      Response.success(
        MockData.mockSourceResponse
      )
    )

    //when we call the sources remote to get sources list
    val sources = sourcesRemote.getSources(language = "", category = "", country = "").orNull()

    val source0 = sources?.first()
    val source1 = sources?.last()


    //then assert that we return correct source items
    assertThat(MockData.mockSource0.name).isEqualTo(source0!!.name)
    assertThat(MockData.mockSource0.url).isEqualTo(source0.url)
    assertThat(MockData.mockSource0.language).isEqualTo(source0.language)
    assertThat(MockData.mockSource0.id).isEqualTo(source0.id)
    assertThat(MockData.mockSource0.description).isEqualTo(source0.description)
    assertThat(MockData.mockSource0.category).isEqualTo(source0.category)

    assertThat(MockData.mockSource3.name).isEqualTo(source1!!.name)
    assertThat(MockData.mockSource3.url).isEqualTo(source1.url)
    assertThat(MockData.mockSource3.language).isEqualTo(source1.language)
    assertThat(MockData.mockSource3.id).isEqualTo(source1.id)
    assertThat(MockData.mockSource3.description).isEqualTo(source1.description)
    assertThat(MockData.mockSource3.category).isEqualTo(source1.category)
  }

  @Test
  fun `should return error when endpoint returns error `() = runBlocking {
    //given that api calls getEverything
    whenever(newsAPI.getSources(language = "", category = "", country = "")).thenReturn(
      Response.error(401,
        "".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
      )
    )
    //when we call the everything remote to get article list
    val sources = sourcesRemote.getSources(language = "", category = "", country = "")


    // then it return error
    assertThat(sources.isLeft()).isTrue()
  }

}