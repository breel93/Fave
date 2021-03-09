package com.kolaemiola.local.mapper

import com.example.core.model.Article
import com.kolaemiola.local.mapper.base.RemoteModelMapper
import com.kolaemiola.local.model.ArticleRemoteModel
import javax.inject.Inject

class ArticleRemoteMapper @Inject constructor():
    RemoteModelMapper<ArticleRemoteModel, Article> {
  override fun mapToData(model: ArticleRemoteModel): Article {
    return Article(title = model.title, author = model.author, description = model.description,
        url = model.url, urlToImage = model.urlToImage, publishedAt = model.publishedAt,
        content = model.content, source = model.source?.name
    )
  }
}
