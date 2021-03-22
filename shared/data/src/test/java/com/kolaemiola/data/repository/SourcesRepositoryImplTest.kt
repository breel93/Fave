package com.kolaemiola.data.repository

import arrow.core.Either
import arrow.core.orNull
import com.google.common.truth.Truth.assertThat
import com.kolaemiola.data.remote.SourcesRemote
import com.kolaemiola.data.utils.MockData
import com.kolaemiola.domain.repository.SourcesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SourcesRepositoryImplTest{
  private val sourcesRemote: SourcesRemote = mock()
  private lateinit var sourcesRepository: SourcesRepository

  @Before
  fun setUp(){
    sourcesRepository = SourcesRepositoryImpl(sourcesRemote = sourcesRemote)
  }

  @Test
  fun `get source list successfully from remote`() = runBlocking {
    //given that we call source from remote
    whenever(sourcesRemote.getSources(category = "",
    language = "", country = "")).thenReturn(Either.right(MockData.mockSources))

    //when repository calls get sources
    val sources = sourcesRepository.getSources(category = "",
      language = "", country = "").first().orNull()

    //then verify sources
    assertThat(sources).isNotEmpty()
  }
}