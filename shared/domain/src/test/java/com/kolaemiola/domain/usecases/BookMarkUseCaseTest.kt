package com.kolaemiola.domain.usecases

import arrow.core.orNull
import com.kolaemiola.domain.fake.BookMarkRepositoryFake
import com.kolaemiola.domain.repository.BookMarkRepository
import com.kolaemiola.domain.usecases.bookmark.BookMarkUseCase
import com.kolaemiola.domain.utils.ResponseType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class BookMarkUseCaseTest{

  private val bookMarkRepositoryFake = BookMarkRepositoryFake()
  private val bookMarkUseCase = BookMarkUseCase(bookMarkRepositoryFake)

  @Test
  fun `should return bookmarked articles successfully`() = runBlockingTest {
    //given that the repository return list of articles
    bookMarkRepositoryFake.responseType=  ResponseType.DATA

    //when we call bookmark uses to get articles list
    val articles = bookMarkUseCase.execute(param = "").first().orNull()

    //then we verify that we return article list
    assertThat(articles).isNotEmpty()
  }
}