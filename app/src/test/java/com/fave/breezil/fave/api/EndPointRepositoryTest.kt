package com.fave.breezil.fave.api

import com.fave.breezil.fave.RxTrampolineSchedulerRule
import com.fave.breezil.fave.model.ArticleResult
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class EndPointRepositoryTest {
  @Rule
  @JvmField
  var testSchedulerRule = RxTrampolineSchedulerRule()

  private lateinit var repository: EndPointRepository

  @Mock
  private lateinit var newsApi  : NewsApi



  @Before
  fun setUp() {
    repository = EndPointRepository(newsApi)
    MockitoAnnotations.initMocks(this)
    newsApi = Mockito.mock(NewsApi::class.java)
  }

  @Test
  fun `should display success message on success`(){
    val articleResult = ArticleResult("200",2, anyString(),ArrayList())
    val mockCall = mock<Single<ArticleResult>>{
    }
//    Mockito.`when`(prefs.getApiKey()).thenReturn(key)
//
//    val animal = Animal("cow",null,null,null,null,null,null)
//
//    val animalList = listOf(animal)
//
//    val testSingle = Single.just(animalList)
//
//    Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
//    whenever(api.getFollowings).thenReturn(Observable.just(item))
    repository.getEverything("query","","",
      "","","",5,2).test().values().first()


  }

  @Test
  fun getHeadline() {
  }

  @Test
  fun getEverything() {
  }
}