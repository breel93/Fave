package com.kolaemiola.data.repository

import arrow.core.Either
import arrow.core.orNull
import com.kolaemiola.core.model.Article
import com.kolaemiola.core.model.CategoryArticle
import com.kolaemiola.core.model.Error
import com.kolaemiola.data.remote.HeadLinesRemote
import com.kolaemiola.domain.repository.HeadLinesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HeadLinesRepositoryImpl @Inject constructor(
  private val headLinesRemote: HeadLinesRemote
): HeadLinesRepository{
  override fun getHeadLines(
    country: String,
    sources: String,
    category: String?,
    query: String,
    pageSize: Int,
    page: Int
  ): Flow<Either<Error, List<Article>>> = flow{
    emit(headLinesRemote.getHeadLines(country = country, sources = sources, category = category,
      query = query, pageSize = pageSize, page = page))
  }

  override fun getCategoriesHeadLines(country: String): Flow<Either<Error, List<CategoryArticle>>>
  = flow {
    val categories = listOf("business","entertainment","general","health","science",
      "sports","technology","politics", "travel")
    val categoryArticles = mutableListOf<CategoryArticle>()
    categories.forEach{ category ->
      val categoryArticle = CategoryArticle()
      val articles = headLinesRemote.getHeadLines(
        country = country, sources = "", category = category, query = "",pageSize = 5, page = 1).orNull()
      categoryArticle.title = category
      categoryArticle.articles = articles
      categoryArticles.add(categoryArticle)
    }
    if(categoryArticles.isNotEmpty()){
      emit(Either.right(categoryArticles))
    }else{
      emit(Either.left(Error(message = "List is empty")))
    }
  }

}