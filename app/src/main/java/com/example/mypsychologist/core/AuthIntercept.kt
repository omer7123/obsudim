package com.example.mypsychologist.core

import android.util.Log
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.remote.authentication.AuthenticationDataSource
import com.example.mypsychologist.domain.entity.authenticationEntity.RefreshToken
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

        if (response.code == 404) {
            response.close()

            Log.e("ER", "Yes")
            val refToken = runBlocking {
                sharedPrefDataSource.getRefreshToken()
            }

            Log.e("ER", refToken)
            if(refToken.isNotEmpty()) {
                try {
                    val refreshResponse = runBlocking { authApi.refreshToken(RefreshToken(refToken))}
                    if (refreshResponse.isSuccessful) {
                        val body = refreshResponse.body()
                        if(body!=null){
                            runBlocking {
                                sharedPrefDataSource.saveToken(body.accessToken)
                                sharedPrefDataSource.saveRefreshToken(body.refreshToken)
                            }
                        }

                        return chain.proceed(request.newBuilder().build())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return response
    }
}