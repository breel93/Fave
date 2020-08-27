package com.fave.breezil.fave.ui.main.bookmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.repository.BookMarkRepository
import com.fave.breezil.fave.util.LiveDataTestUtil
import com.fave.breezil.fave.util.MockTestUtil
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BookMarkViewModelTest {

  private var bookMarkViewModel: BookMarkViewModel? = null

  @Mock
  private lateinit var repository: BookMarkRepository

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    bookMarkViewModel = BookMarkViewModel(repository)
  }



  @Test
  fun getBookmarkList() = runBlocking {

  }

  @Test
  fun `insert article into room test`() = runBlocking {
    val mockTestUtil = MockTestUtil()
    val mockArticle = mockTestUtil.mockArticle()
    bookMarkViewModel!!.insert(mockArticle)
  }

  @Test
  fun delete()  = runBlocking{
  }

  @After
  fun tearDown() {
  }
}