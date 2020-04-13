package com.fave.breezil.fave.repository.headlines

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.fave.breezil.fave.BuildConfig.API_KEY
import com.fave.breezil.fave.api.NewsApi
import com.fave.breezil.fave.model.Sources
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceRepository  @Inject
internal constructor(private val newsApi : NewsApi){

    internal val soureList = MutableLiveData<List<Sources>>()

    @SuppressLint("CheckResult")
    fun getSources(category: String, language: String, country: String) : MutableLiveData<List<Sources>> {
        newsApi.getSources(category,language,country,API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    it.sources
                    soureList.postValue(it.sources) },  { throwable -> throwable.printStackTrace()})

        return soureList
    }
}