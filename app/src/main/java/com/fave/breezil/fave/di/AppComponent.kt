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
package com.fave.breezil.fave.di

import android.app.Application
import com.fave.breezil.fave.Fave
import com.fave.breezil.fave.di.module.MainActivityModule
import com.fave.breezil.fave.di.module.AppModule
import com.fave.breezil.fave.di.module.SettingsActivityModule
import com.fave.breezil.fave.di.module.SplashActivityModule
import com.fave.breezil.fave.di.module.ActionBottomSheetModule
import com.fave.breezil.fave.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
  modules = [AndroidInjectionModule::class,
    AppModule::class,
    MainActivityModule::class,
    SettingsActivityModule::class,
    SplashActivityModule::class,
    ActionBottomSheetModule::class,
    ViewModelModule::class]
)
interface AppComponent : AndroidInjector<Fave> {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }
}
