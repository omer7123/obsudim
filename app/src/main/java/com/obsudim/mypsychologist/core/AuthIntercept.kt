package com.obsudim.mypsychologist.core

import com.obsudim.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.obsudim.mypsychologist.data.remote.authentication.AuthenticationDataSource
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.RefreshToken
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authApi: AuthenticationDataSource,
    private val sharedPrefDataSource: AuthenticationSharedPrefDataSource,
) : Interceptor {

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {


                val refToken = runBlocking {
                    sharedPrefDataSource.getRefreshToken()
                }

                if (refToken.isNotEmpty()) {
                    try {
                        val refreshResponse =
                            runBlocking { authApi.refreshToken(RefreshToken(refToken)) }
                        if (refreshResponse.isSuccessful) {
                            val body = refreshResponse.body()
                            if (body != null) {
                                runBlocking {
                                    sharedPrefDataSource.saveToken(body.accessToken)
                                    sharedPrefDataSource.saveRefreshToken(body.refreshToken)
                                }

                                    val newCookies = refreshResponse.headers().values("Set-Cookie")
                                    if (newCookies.isNotEmpty()) {
                                        CookieStorage.saveCookies(newCookies)
                                    }

                            }

                            return chain.proceed(request.newBuilder().addHeader("Cookie",
                                CookieStorage.getCookies().joinToString("; ")).build())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
            }
        }

        return response
    }
}