package com.fave.breezil.fave.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fave.breezil.fave.RxTrampolineSchedulerRule
import com.fave.breezil.fave.db.ArticleDao
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.util.LiveDataTestUtil
import com.fave.breezil.fave.util.MockTestUtil
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

  @get:Rule
  var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  var classRule: RxTrampolineSchedulerRule = RxTrampolineSchedulerRule()

  @Before
  fun setUp() {
    bookMarkRepository = BookMarkRepository(articleDao!!)
  }


  @Test
  fun getBookMarks() {
    // Arrange
    val mockTestUtil = MockTestUtil()
    val articles: List<Article> = mockTestUtil.mockArticleList()
    val liveDataTestUtil: LiveDataTestUtil<List<Article>> = LiveDataTestUtil()
    val returnedData: MutableLiveData<List<Article>> =
      MutableLiveData()
    returnedData.value = articles

    Mockito.`when`(articleDao!!.allBookMarks).thenReturn(returnedData)

    // Act

    // Act
    val observedData: List<Article> =
      liveDataTestUtil.getValue(bookMarkRepository.getBookMarks())!!

    // Assert
    assertEquals(articles, observedData)
  }

  @Test
  fun insertBookMark() {
    val mockTestUtil = MockTestUtil()
    val mockArticle = mockTestUtil.mockArticle()
    bookMarkRepository.insertBookMark(mockArticle)
    Mockito.verify(articleDao)!!.insert(mockArticle)
  }

  @Test
  fun deleteBookMark() {
    val mockTestUtil = MockTestUtil()
    val mockArticle = mockTestUtil.mockArticle()
    bookMarkRepository.deleteBookMark(mockArticle)
    Mockito.verify(articleDao, Mockito.atLeast(0))!!.delete(mockArticle)
  }


  @After
  fun tearDown() {
  }
}

