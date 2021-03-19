package com.kolaemiola.data.di

import com.kolaemiola.data.repository.BookMarkRepositoryImpl
import com.kolaemiola.data.repository.EverythingRepositoryImpl
import com.kolaemiola.data.repository.HeadLinesRepositoryImpl
import com.kolaemiola.data.repository.SourcesRepositoryImpl
import com.kolaemiola.domain.repository.BookMarkRepository
import com.kolaemiola.domain.repository.EverythingRepository
import com.kolaemiola.domain.repository.HeadLinesRepository
import com.kolaemiola.domain.repository.SourcesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
  @get:Binds
  val BookMarkRepositoryImpl.bookmarkRepository: BookMarkRepository

  @get:Binds
  val EverythingRepositoryImpl.everythingRepository: EverythingRepository

  @get:Binds
  val HeadLinesRepositoryImpl.headLinesRepository: HeadLinesRepository

  @get:Binds
  val SourcesRepositoryImpl.sourcesRepository: SourcesRepository
}