package com.kolaemiola.local.mapper.base

interface RemoteModelMapper<R,  D> {
  fun mapToData(model: R): D
  fun mapToDataList(models: List<R>): List<D> {
    return models.mapTo(mutableListOf(), ::mapToData)
  }
}