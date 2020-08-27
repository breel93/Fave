package com.fave.breezil.fave.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fave.breezil.fave.db.ArticleDao
import com.fave.breezil.fave.util.MainCoroutinesRule
import com.fave.breezil.fave.util.MockTestUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookMarkRepositoryTest {

  @Mock
  var articleDao: ArticleDao? = null

  lateinit var bookMarkRepository : BookMarkRepository

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setUp() {
    bookMarkRepository = BookMarkRepository(articleDao!!)
  }


  @Test
  fun getBookMarks() = runBlocking{
//    // Arrange
//    val mockTestUtil = MockTestUtil()
//    val articles: List<Article> = mockTestUtil.mockArticleList()
//    val liveDataTestUtil: LiveDataTestUtil<List<Article>> = LiveDataTestUtil()
//    val returnedData: MutableLiveData<List<Article>> =
//      MutableLiveData()
//    returnedData.value = articles
//
//    Mockito.`when`(articleDao!!.allBookMarks).thenReturn(returnedData)
//
//    // Act
//
//    // Act
//    val observedData: List<Article> =
//      liveDataTestUtil.getValue(bookMarkRepository.getBookMarks())!!
//
//    // Assert
//    assertEquals(articles, observedData)
  }

  @Test
  fun insertBookMark() = runBlocking {
    val mockTestUtil = MockTestUtil()
    val mockArticle = mockTestUtil.mockArticle()
    bookMarkRepository.insert(mockArticle)
    Mockito.verify(articleDao)!!.insert(mockArticle)
  }

  @Test
  fun deleteBookMark()= runBlocking {
    val mockTestUtil = MockTestUtil()
    val mockArticle = mockTestUtil.mockArticle()
    bookMarkRepository.delete(mockArticle)
    Mockito.verify(articleDao, Mockito.atLeast(0))!!.delete(mockArticle)
  }


  @After
  fun tearDown() {
  }
}

