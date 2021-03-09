package com.kolaemiola.local.mapper

import com.example.core.model.Article
import com.kolaemiola.local.entities.ArticleEntity
import com.kolaemiola.local.mapper.base.LocalMapper
import javax.inject.Inject

internal class ArticleLocalMapper @Inject constructor() :
    LocalMapper<Article, ArticleEntity> {
  override fun mapToData(entity: ArticleEntity): Article {
    return Article(title = entity.title, author = entity.author, description = entity.description,
        url = entity.url, urlToImage = entity.urlToImage, publishedAt = entity.publishedAt,
        content = entity.content, source = entity.source

    )
  }

  override fun mapToLocal(model: Article): ArticleEntity {
    return ArticleEntity(title = model.title, author = model.author, description = model.description,
        url = model.url, urlToImage = model.urlToImage, publishedAt = model.publishedAt,
        content = model.content, source = model.source
    )
  }

}

