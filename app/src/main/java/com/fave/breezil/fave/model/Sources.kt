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
package com.fave.breezil.fave.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sources(
  @SerializedName("id")
  @Expose
  var id: String,
  @SerializedName("name")
  @Expose
  var name: String,
  @SerializedName("description")
  @Expose
  var description: String?,
  @SerializedName("url")
  @Expose
  var url: String,
  @SerializedName("category")
  @Expose
  var category: String,
  @SerializedName("language")
  @Expose
  var language: String,
  @SerializedName("country")
  @Expose
  var country: String
) : Parcelable
