/**
 *  Designed and developed by Fave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.fave.breezil.fave.di.module

import com.fave.breezil.fave.ui.main.bookmark.BookMarkedFragment
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
