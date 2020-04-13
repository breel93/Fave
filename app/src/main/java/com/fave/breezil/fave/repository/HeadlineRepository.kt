package com.fave.breezil.fave.repository

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import android.content.Context
import com.fave.breezil.fave.BuildConfig.API_KEY
import com.fave.breezil.fave.R
import com.fave.breezil.fave.api.NewsApi
import com.fave.breezil.fave.model.Articles
import com.fave.breezil.fave.model.ParentModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList


@SuppressLint("CheckResult")

@Singleton
class HeadlineRepository @Inject
internal constructor(private val newsApi: NewsApi){

    private var trendCategoryList: List<String>? = null
    internal var isSuccessful = MutableLiveData<Boolean>()
    internal val breakingNewList = MutableLiveData<List<Articles>>()



    fun getArticleSuccessful(context: Context, parentList: ArrayList<ParentModel>,
                             country: String, sources: String, query: String
    ): MutableLiveData<Boolean> {
        val textArray = context.resources.getStringArray(R.array.category_list)
        trendCategoryList = Arrays.asList(*textArray)
        Collections.shuffle(trendCategoryList)

        for (item in (trendCategoryList as MutableList<String>?)!!) {

            val parentModel = ParentModel()
            val articles = ArrayList<Articles>()
            parentModel.title = item
            newsApi.getHeadlines(country,sources,item,query,7,1, API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({
                        articles.addAll(it.articles)
                        isSuccessful.postValue(true) },
                            { throwable -> throwable.printStackTrace() })

            parentModel.articles = articles
            parentList.add(parentModel)
        }

        return isSuccessful

    }


    fun getBreakingNewsArticles(sources: String,sortBy: String?,from: String?,to: String?,language: String?)
    : MutableLiveData<List<Articles>>{
        newsApi.getBreakingNews("",sources,sortBy,from,to,language,20,1, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    breakingNewList.postValue(it.articles)},
                        { throwable -> throwable.printStackTrace()})
        return breakingNewList

    }


}