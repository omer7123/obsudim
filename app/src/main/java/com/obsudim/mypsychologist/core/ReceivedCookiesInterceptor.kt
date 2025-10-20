package com.obsudim.mypsychologist.core

import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.headers("Set-Cookie").isNotEmpty()) {
            val cookies = response.headers("Set-Cookie")
            CookieStorage.saveCookies(cookies)
        }
        
        return response
    }
}