package com.kolaemiola.remote.api.interceptor

import com.kolaemiola.remote.BuildConfig.NEWS_API_KEY
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