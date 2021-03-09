package com.example.data.mapper.base

interface DataMapper<D, R, E> {
  fun mapToData(model: R): D
  fun mapToDataList(models: List<R>): List<D> {
    return models.mapTo(mutableListOf(), ::mapToData)
  }

  fun mapToRemote(model: D): R
  fun mapToRemoteList(models: List<D>): List<R> {
    return models.mapTo(mutableListOf(), ::mapToRemote)
  }
}


