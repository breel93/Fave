package com.kolaemiola.remote.mapper.base


interface LocalMapper<D, E> {
  fun mapToData(entity: E): D
  fun mapToDataList(entity: List<E>): List<D> {
    return entity.mapTo(mutableListOf(), ::mapToData)
  }

  fun mapToLocal(model: D): E
  fun mapToLocalList(models: List<D>): List<E> {
    return models.mapTo(mutableListOf(), ::mapToLocal)
  }
}



