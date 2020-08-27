package com.fave.breezil.fave.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fave.breezil.fave.api.EndPointRepository
import app.cash.turbine.test
import com.fave.breezil.fave.util.MainCoroutinesRule
import com.fave.breezil.fave.util.MockTestUtil
import com.fave.breezil.fave.utils.Result
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class HeadlineRepositoryTest {

  @Mock
  private lateinit var endPointRepository: EndPointRepository


  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  lateinit var headlineRepository: HeadlineRepository

  @ExperimentalCoroutinesApi
  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    headlineRepository = HeadlineRepository(endPointRepository)
  }

  @ExperimentalTime
  @Test
  fun getBreakingNewsArticlesTest() = runBlocking {
    val mockTestUtil = MockTestUtil()
    whenever(endPointRepository.fetchEverything("","cnn","","",
      "","en",10,1)).
    thenReturn(Result.success(mockTestUtil.mockArticleResponse()))

    headlineRepository.fetchBreakingNewsArticles("","cnn","","",
      "en",
      onSuccess = {},
      onError = {}).test {
      assertEquals("expectItem()[0].url","")
      expectComplete()
    }
  }
  @Test
  fun deleteArticleTest()= runBlocking{

  }

  @Test
  fun getArticleSuccessful(){

  }



  @After
  fun tearDown() {}
}