package com.kolaemiola.data.room.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kolaemiola.data.room.base.AppDatabaseTest
import com.kolaemiola.data.utils.MockData
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ArticleDaoTest: AppDatabaseTest(){

  @Test
  fun `should get articles list successfully`() = runBlocking {
    //given
    articleDao.insert(MockData.mockArticleEntity0)

    //when
    val articles = articleDao.getArticles()

    //then
    assertThat(articles).isNotEmpty()
  }

  @Test
  fun `should insert article successfully`() = runBlocking {
    //given
    articleDao.insert(MockData.mockArticleEntity2)

    //when
    val article = articleDao.getArticles()[0]

    //then
    assertThat(MockData.mockArticleEntity2).isEqualTo(article)
  }
  @Test
  fun `should delete article successfully`() = runBlocking {
    //given
    articleDao.insert(MockData.mockArticleEntity0)

    val insertedArticle = articleDao.getArticles()[0]

    //when
    articleDao.delete(insertedArticle)

    //then
    assertThat(articleDao.getArticles()).isEmpty()
  }

}