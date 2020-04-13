package com.fave.breezil.fave.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.fave.breezil.fave.ui.main.bookmark.BookMarkViewModel
import com.fave.breezil.fave.ui.main.top_stories.MainViewModel
import com.fave.breezil.fave.ui.main.my_feeds.PreferredViewModel
import com.fave.breezil.fave.ui.main.sources.SourcesViewModel
import com.fave.breezil.fave.view_model.ViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindmainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PreferredViewModel::class)
    internal abstract fun bindPreferredViewModel(preferredViewModel: PreferredViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(BookMarkViewModel::class)
    internal abstract fun bindBookMarkViewModel(bookMarkViewModel: BookMarkViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SourcesViewModel::class)
    internal abstract fun bindSourcesViewModel(sourcesViewModel: SourcesViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}