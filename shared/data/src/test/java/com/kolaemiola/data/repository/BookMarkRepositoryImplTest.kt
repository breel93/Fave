package com.kolaemiola.data.repository

import com.kolaemiola.data.local.BookMarkLocal
import com.kolaemiola.data.utils.MockData
import com.kolaemiola.domain.repository.BookMarkRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class BookMarkRepositoryImplTest{
  private val bookMarkLocal: BookMarkLocal = mock()
  private lateinit var bookMarkRepository: BookMarkRepository

  @Before
  fun setUp(){
    bookMarkRepository = BookMarkRepositoryImpl(bookMarkLocal = bookMarkLocal)
  }

  @Test
  fun `get bookmark list article successfully from local`() = runBlocking {
    //given that we call bookmark from local
    whenever(bookMarkLocal.getBookMarks()).thenReturn(MockData.mockArticles)

    //when repository calls bookmarks
    val bookmarks = bookMarkRepository.getBookMarks().first()

    //then verify articles
    assertThat(bookmarks).isNotEmpty()
  }

}