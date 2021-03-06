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

import com.google.gson.annotations.SerializedName

data class ArticleResult(
  @SerializedName("status")
  var status: String,
  @SerializedName("totalResults")
  var totalResults: Int = 0,

  @SerializedName("message")
  var message: String?,

  @SerializedName("articles")
  var articles: List<Article>
)
