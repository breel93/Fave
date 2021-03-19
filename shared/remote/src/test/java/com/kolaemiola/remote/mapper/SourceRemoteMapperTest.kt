package com.kolaemiola.remote.mapper

import com.kolaemiola.remote.utils.MockData
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class SourceRemoteMapperTest{
  private val sourceRemoteMapper = SourceRemoteMapper()

  @Test
  fun `should map SourceRemoteModel to Source correctly`(){
    //given data SourceRemoteModel model
    val sourceRemoteModel = MockData.mockSource0

    //when SourceRemoteModel maps to source
    val source = sourceRemoteMapper.mapToData(sourceRemoteModel)

    //then verify that SourceRemoteModel maps correctly Source
    assertThat(sourceRemoteModel.id).isEqualTo(source.id)
    assertThat(sourceRemoteModel.category).isEqualTo(source.category)
    assertThat(sourceRemoteModel.country).isEqualTo(source.country)
    assertThat(sourceRemoteModel.description).isEqualTo(source.description)
    assertThat(sourceRemoteModel.language).isEqualTo(source.language)
    assertThat(sourceRemoteModel.name).isEqualTo(source.name)
    assertThat(sourceRemoteModel.url).isEqualTo(source.url)
  }
}