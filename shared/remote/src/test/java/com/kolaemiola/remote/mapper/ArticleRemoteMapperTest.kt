package com.kolaemiola.remote.mapper

import com.kolaemiola.remote.utils.MockData
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class ArticleRemoteMapperTest{
  private val articleRemoteMapper = ArticleRemoteMapper()

  @Test
  fun `should map ArticleRemoteModel to Article correctly`(){
    //given data ArticleRemoteModel model
    val articleRemoteModel = MockData.mockArticle0

    //when ArticleRemoteModel maps to Article
    val article = articleRemoteMapper.mapToData(articleRemoteModel)

    //then verify that ArticleRemoteModel maps correctly to Article
    assertThat(articleRemoteModel.title).isEqualTo(article.title)
    assertThat(articleRemoteModel.publishedAt).isEqualTo(article.publishedAt)
    assertThat(articleRemoteModel.description).isEqualTo(article.description)
    assertThat(articleRemoteModel.url).isEqualTo(article.url)
    assertThat(articleRemoteModel.author).isEqualTo(article.author)
    assertThat(articleRemoteModel.content).isEqualTo(article.content)
    assertThat(articleRemoteModel.source!!.name).isEqualTo(article.source)
    assertThat(articleRemoteModel.urlToImage).isEqualTo(article.urlToImage)
  }
}