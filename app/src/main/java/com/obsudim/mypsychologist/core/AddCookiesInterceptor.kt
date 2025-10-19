package com.obsudim.mypsychologist.core

import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val cookies = CookieStorage.getCookies()
        if (cookies.isNotEmpty()) {
            request.addHeader("Cookie", cookies.joinToString("; "))
        }

        return chain.proceed(request.build())
    }
}
