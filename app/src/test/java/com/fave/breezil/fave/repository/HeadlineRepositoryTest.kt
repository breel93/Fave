package com.fave.breezil.fave.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fave.breezil.fave.api.NewsApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HeadlineRepositoryTest {

  @Mock
  private lateinit var newsApi  : NewsApi

  @get:Rule
  var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  lateinit var headlineRepository: HeadlineRepository

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    headlineRepository = HeadlineRepository(newsApi)
  }

  @Test
  fun getArticleSuccessful(){

  }

  @Test
  fun getBreakingNewsArticles(){

  }

  @After
  fun tearDown() {}
}