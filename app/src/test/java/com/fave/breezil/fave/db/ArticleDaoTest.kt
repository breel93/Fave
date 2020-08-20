package com.fave.breezil.fave.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.util.LiveDataTestUtil
import com.fave.breezil.fave.util.MockTestUtil
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest : AppDatabaseTest(){
  @get:Rule
  var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  @Test
  fun insertArticleTest(){
    val mockTestUtil = MockTestUtil()
    val mockArticle = mockTestUtil.mockArticle(5)
    db!!.articleDao().insert(mockArticle)
    val article = db!!.articleDao().getArticleById(5)
    assertEquals(5, article.id)
  }

  @Test
  fun deleteArticleTest(){
    val mockTestUtil = MockTestUtil()
    val mockArticle = mockTestUtil.mockArticle(5)
    db!!.articleDao().delete(mockArticle)
    val article = db!!.articleDao().getArticleById(5)
    Assert.assertNull(article)
  }

  @Test
  fun getArticlesTest() {
    val mockTestUtil = MockTestUtil()
    val mockArticle = mockTestUtil.mockArticle()
    db!!.articleDao().insert(mockArticle)
    db!!.articleDao().insert(mockArticle)

    val liveDataTestUtil: LiveDataTestUtil<List<Article>> = LiveDataTestUtil()
    val articleList = liveDataTestUtil.getValue(db!!.articleDao().allBookMarks)
    assertNotNull(articleList)
    assertEquals(mockArticle.id, 0)
  }
}