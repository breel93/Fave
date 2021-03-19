package com.kolaemiola.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.local.BookMarkLocal
import com.kolaemiola.data.mapper.ArticleLocalMapper
import com.kolaemiola.data.room.dao.ArticleDao
import com.kolaemiola.data.utils.MockData
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BookMarkLocalImplTest{
  private val articleDao: ArticleDao = mock()
  private val articleLocalMapper = ArticleLocalMapper()
  private lateinit var bookMarkLocal: BookMarkLocal

  @Before
  fun setup(){
    bookMarkLocal = BookMarkLocalImpl(articleDao = articleDao,
      articleLocalMapper = articleLocalMapper)
  }

  @Test
  fun `should get articles successfully`() = runBlocking {
    //given that articles is saved in db
    articleDao.insert(MockData.mockArticleEntity1)

    //when we call the bookmark local
    val article = bookMarkLocal.getBookMarks().first()

    // then verify
    assertThat(article).isEqualTo(MockData.mockArticle0)
  }


  @Test
  fun `should insert article successfully`() = runBlocking {
    //given that articles is saved in db
    articleDao.insert(MockData.mockArticleEntity1)

    //when we call the bookmark local
    val article = bookMarkLocal.getBookMarks().first()

    // then verify
    assertThat(article).isEqualTo(MockData.mockArticle0)
  }

  @Test
  fun `should delete article successfully`() = runBlocking {
    //given that articles is saved in db
    articleDao.insert(MockData.mockArticleEntity1)

    //when we call the bookmark local
    val article = bookMarkLocal.getBookMarks().first()

    // then verify
    assertThat(article).isEqualTo(MockData.mockArticle0)
  }

}