package com.kolaemiola.local.mapper


import com.google.common.truth.Truth.assertThat
import com.kolaemiola.data.utils.MockData
import com.kolaemiola.local.mapper.ArticleLocalMapper
import org.junit.Test

class ArticleLocalMapperTest {
  private val articleLocalMapper = ArticleLocalMapper()

  @Test
  fun `should map ArticleLocalModel to Article correctly`() {
    //given data ArticleLocalModel model
    val articleLocalModel = MockData.mockArticleEntity0

    //when ArticleRemoteModel maps to Article
    val article = articleLocalMapper.mapToData(articleLocalModel)

    //then verify that ArticleRemoteModel maps correctly to Article
    assertThat(articleLocalModel.title).isEqualTo(article.title)
    assertThat(articleLocalModel.publishedAt).isEqualTo(article.publishedAt)
    assertThat(articleLocalModel.description).isEqualTo(article.description)
    assertThat(articleLocalModel.url).isEqualTo(article.url)
    assertThat(articleLocalModel.author).isEqualTo(article.author)
    assertThat(articleLocalModel.content).isEqualTo(article.content)
    assertThat(articleLocalModel.source).isEqualTo(article.source)
    assertThat(articleLocalModel.urlToImage).isEqualTo(article.urlToImage)
  }

  @Test
  fun `should map Article to ArticleLocalModel correctly`() {
    //given data ArticleModel model
    val articleModel = MockData.mockArticle0

    //when Article maps to ArticleRemoteModel
    val article = articleLocalMapper.mapToLocal(articleModel)

    //then verify that Article maps correctly to ArticleRemoteModel
    assertThat(articleModel.title).isEqualTo(article.title)
    assertThat(articleModel.publishedAt).isEqualTo(article.publishedAt)
    assertThat(articleModel.description).isEqualTo(article.description)
    assertThat(articleModel.url).isEqualTo(article.url)
    assertThat(articleModel.author).isEqualTo(article.author)
    assertThat(articleModel.content).isEqualTo(article.content)
    assertThat(articleModel.source).isEqualTo(article.source)
    assertThat(articleModel.urlToImage).isEqualTo(article.urlToImage)
  }
}