package com.kolaemiola.data.api.interceptor

import com.kolaemiola.data.BuildConfig.NEWS_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()
    val originalHttpUrl = originalRequest.url
    val newUrl = originalHttpUrl.newBuilder()
      .addQueryParameter("apiKey", NEWS_API_KEY)
      .build()
    val request = originalRequest.newBuilder()
      .url(newUrl)
      .build()
    return chain.proceed(request)
  }
}