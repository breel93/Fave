package com.fave.breezil.fave.ui.main.sources

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.repository.headlines.SourceRepository
import javax.inject.Inject

class SourcesViewModel @Inject
constructor( private val sourceRepository: SourceRepository, application: Application) : AndroidViewModel(application) {


    fun getSourcesList(category: String, language: String, country: String) : MutableLiveData<List<Sources>>{
        return sourceRepository.getSources(category, language, country)
    }
}