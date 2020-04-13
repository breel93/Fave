package com.fave.breezil.fave.repository.headlines

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

import com.fave.breezil.fave.model.Articles

import javax.inject.Inject

class HeadlineDataSourceFactory @Inject
constructor(val headlineDataSource: HeadlineDataSource) : DataSource.Factory<Int, Articles>() {
    val headlineDataSourceMutableLiveData: MutableLiveData<HeadlineDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Articles> {
        headlineDataSourceMutableLiveData.postValue(headlineDataSource)
        return headlineDataSource
    }
}
