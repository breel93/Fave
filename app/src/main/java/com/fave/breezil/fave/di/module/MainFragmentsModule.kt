package com.fave.breezil.fave.di.module

import com.fave.breezil.fave.ui.main.bookmark.BookMarkedFragment
import com.fave.breezil.fave.ui.main.my_feeds.MyFeedsFragment
import com.fave.breezil.fave.ui.main.sources.SourceDetailFragment
import com.fave.breezil.fave.ui.main.sources.SourcesFragment
import com.fave.breezil.fave.ui.main.top_stories.CategoryArticlesFragment
import com.fave.breezil.fave.ui.main.top_stories.MainFragment
import com.fave.breezil.fave.ui.main.top_stories.SearchFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentsModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun contributeMyFeedsFragment(): MyFeedsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeBookMarkedFragment(): BookMarkedFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSourcesFragment(): SourcesFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCategoryArticlesFragment(): CategoryArticlesFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSourceDetailFragment(): SourceDetailFragment

}
