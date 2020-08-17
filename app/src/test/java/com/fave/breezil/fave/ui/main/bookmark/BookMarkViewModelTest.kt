package com.fave.breezil.fave.ui.main.bookmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.repository.BookMarkRepository
import com.fave.breezil.fave.util.LiveDataTestUtil
import com.fave.breezil.fave.util.MockTestUtil
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
  fun getBookmarkList() {
    // arrange
    val mockTestUtil = MockTestUtil()
    val articleList: List<Article> = mockTestUtil.mockArticleList()
    val liveDataTestUtil: LiveDataTestUtil<List<Article>> = LiveDataTestUtil()
    val returnedValue: MutableLiveData<List<Article>> =
      MutableLiveData<List<Article>>()
    returnedValue.value = articleList
    Mockito.`when`(repository.getBookMarks()).thenReturn(returnedValue)

    // act
    bookMarkViewModel!!.bookmarkList
    val observedData: List<Article> =
      liveDataTestUtil.getValue(bookMarkViewModel!!.bookmarkList)!!


    // assert
    Assert.assertEquals(articleList, observedData)
  }

  @Test
  fun `insert article into room test`() {
    val mockTestUtil = MockTestUtil()
    val article: Article = mockTestUtil.mockArticle()
    val expected = "Successful"
    val liveDataTestUtil: LiveDataTestUtil<String> = LiveDataTestUtil()
    val returnData = MutableLiveData<String>()
    returnData.value = expected
    Mockito.`when`(repository.insertBookMark(article)).thenReturn(returnData)
    // act
    bookMarkViewModel!!.insert(article)
    val observedResult: String =
      liveDataTestUtil.getValue(bookMarkViewModel!!.insert(article))!!

    Assert.assertEquals(expected, observedResult)
  }

  @Test
  fun delete() {
    val mockTestUtil = MockTestUtil()
    val article: Article = mockTestUtil.mockArticle()
    val expected = "Successful"
    val liveDataTestUtil: LiveDataTestUtil<String> = LiveDataTestUtil()
    val returnData = MutableLiveData<String>()
    returnData.value = expected
    repository.insertBookMark(article)
    Mockito.`when`(repository.deleteBookMark(article)).thenReturn(returnData)

    bookMarkViewModel!!.insert(article)
    val observedResult: String =
      liveDataTestUtil.getValue(bookMarkViewModel!!.delete(article))!!

    Assert.assertEquals(expected, observedResult)
  }

  @After
  fun tearDown() {
  }
}