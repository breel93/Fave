package com.kolaemiola.data.mapper

import com.example.core.model.Source
import com.kolaemiola.data.mapper.base.RemoteModelMapper
import com.kolaemiola.data.model.SourceRemoteModel
import javax.inject.Inject

class SourceRemoteMapper @Inject constructor() :
    RemoteModelMapper<SourceRemoteModel, Source> {
  override fun mapToData(model: SourceRemoteModel): Source =
      Source(id = model.id, name = model.name, description = model.description, url = model.url,
          category = model.category, language = model.language, country = model.country
      )
}
