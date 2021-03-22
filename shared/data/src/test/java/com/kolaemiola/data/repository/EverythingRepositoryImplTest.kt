package com.kolaemiola.data.repository

import arrow.core.Either
import arrow.core.orNull
import com.kolaemiola.data.remote.EverythingRemote
import com.kolaemiola.data.utils.MockData
import com.kolaemiola.domain.repository.EverythingRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class EverythingRepositoryImplTest{
  private val everythingRemote: EverythingRemote = mock()
  private lateinit var everythingRepository: EverythingRepository

  @Before
  fun setUp(){
    everythingRepository = EverythingRepositoryImpl(everythingRemote = everythingRemote)
  }

  @Test
  fun `get everything list article successfully from remote`() = runBlocking{
    //given that we call get everything from remote
    val sources = "bleacher-reports,reuters,cnn,msnbc"
    whenever(everythingRemote.getEverything(
      query = "", sources = "", sortBy = "", from = "", to = "", language = "",
      pageSize = 5, page = 1
    )).thenReturn(Either.right(MockData.mockArticles))


    //when repository calls get everything
    val sourcesList = listOf("bleacher-reports", "reuters", "cnn", "msnbc")
    val everything = everythingRepository.getEverything(query = "", sources = listOf(), sortBy = "", from = "",
      to = "", language = "", pageSize = 5, page = 1).first().orNull()

    //then verify articles
    assertThat(everything).isNotEmpty()
  }
}