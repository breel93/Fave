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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fave.breezil.fave.ui.main.bookmark.BookMarkViewModel
import com.fave.breezil.fave.ui.main.look_up.LookUpViewModel
import com.fave.breezil.fave.ui.main.sources.SourcesViewModel
import com.fave.breezil.fave.ui.main.top_stories.MainViewModel
import com.fave.breezil.fave.view_model.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(LookUpViewModel::class)
  internal abstract fun bindPreferredViewModel(lookUpViewModel: LookUpViewModel): ViewModel

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
