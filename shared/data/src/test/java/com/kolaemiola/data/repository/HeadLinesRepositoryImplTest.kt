package com.kolaemiola.data.repository

import arrow.core.Either
import arrow.core.orNull
import com.google.common.truth.Truth.assertThat
import com.kolaemiola.data.remote.HeadLinesRemote
import com.kolaemiola.data.utils.MockData
import com.kolaemiola.domain.repository.HeadLinesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HeadLinesRepositoryImplTest{
  private val headLinesRemote: HeadLinesRemote = mock()
  private lateinit var headLinesRepository: HeadLinesRepository

  @Before
  fun setUp(){
    headLinesRepository = HeadLinesRepositoryImpl(headLinesRemote = headLinesRemote)
  }

  @Test
  fun `get headline list article successfully from remote`() = runBlocking{
    //given that we call get headline from remote
    whenever(headLinesRemote.getHeadLines(
      country = "", sources = "", category = "",
      query = "", pageSize = 5, page = 1
    )).thenReturn(Either.right(MockData.mockArticles))

    //when repository calls get headline
    val headlines = headLinesRepository.getHeadLines( country = "", sources = "", category = "",
      query = "", pageSize = 5, page = 1).first().orNull()

    //then verify articles
    assertThat(headlines).isNotEmpty()

  }

  @Test
  fun `get categories headline list article successfully from remote`() = runBlocking{
    //given that we call get headline from remote
    whenever(headLinesRemote.getHeadLines(
      country = "", sources = "", category = "",
      query = "", pageSize = 5, page = 1
    )).thenReturn(Either.right(MockData.mockArticles))

    //when repository calls get category headline
    val categoryHeadlines = headLinesRepository.getCategoriesHeadLines( country = "").first().orNull()

    //then verify articles
    assertThat(categoryHeadlines).isNotEmpty()

  }
}