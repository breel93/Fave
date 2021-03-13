package com.kolaemiola.local.mapper

import com.example.core.model.Source
import com.kolaemiola.local.mapper.base.RemoteModelMapper
import com.kolaemiola.local.model.SourceRemoteModel
import javax.inject.Inject

class SourceRemoteMapper @Inject constructor() :
    RemoteModelMapper<SourceRemoteModel, Source> {
  override fun mapToData(model: SourceRemoteModel): Source =
      Source(id = model.id, name = model.name, description = model.description, url = model.url,
          category = model.category, language = model.language, country = model.country
      )
}
