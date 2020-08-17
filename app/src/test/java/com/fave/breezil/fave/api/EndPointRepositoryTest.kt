package com.fave.breezil.fave.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fave.breezil.fave.BuildConfig.NEWS_API_KEY
import com.fave.breezil.fave.RxTrampolineSchedulerRule
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.ArticleResult
import com.fave.breezil.fave.model.Source
import com.fave.breezil.fave.util.MockTestUtil
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class EndPointRepositoryTest {

  @get:Rule
  var testSchedulerRule = InstantTaskExecutorRule()

  @get:Rule
  var rule = RxTrampolineSchedulerRule()

  private lateinit var repository: EndPointRepository

  @Mock
  private lateinit var newsApi  : NewsApi



  @Before
  fun setUp() {
    repository = EndPointRepository(newsApi)
    MockitoAnnotations.initMocks(this)
  }



  @Test
  fun `should display success message on success`(){
    val mockTestUtil = MockTestUtil()
    val article = Article ( "PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",
      "In today’s IGN Daily Fix, Sydnee Goodman talks PlayStation Plus free games for July 2020 and reports that an Xbox Series S (aka Project Lockhart) announcemen...",
      "https://www.youtube.com/watch?v=9SHwHJYIT7Q",
      "https://i.ytimg.com/vi/9SHwHJYIT7Q/maxresdefault.jpg",
      "2020-06-29T22:31:54Z",
      Source( "YouTube")
    )
    val articleList: List<Article> = arrayListOf(article)

    val mockResult = ArticleResult("ok", 0, "", articleList)

//    `when`(newsApi.getHeadline(
//      "us","","technology",
//      "",5,1, NEWS_API_KEY)).thenReturn(Single.just(mockResult))
    `when`(newsApi.getHeadline(
      anyString(),anyString(),anyString(),
      anyString(), anyInt(), anyInt(), anyString())).thenReturn(Single.just(mockResult))

    //act
    repository.getHeadline("us","","technology",
      "",5,1)

    //assert
    verify{ newsApi.getHeadline("us","","technology",
      "",5,1, NEWS_API_KEY) }

//    val testObserver: TestObserver<T> = TestObserver<Any?>()
//    data.subscribe(testObserver)
//    testObserver.assertNoErrors()
  }

  @Test
  fun getHeadline() {
  }

  @Test
  fun getEverything() {
  }
}

