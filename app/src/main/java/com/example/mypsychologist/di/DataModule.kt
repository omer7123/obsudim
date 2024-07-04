package com.example.mypsychologist.di

import com.example.mypsychologist.core.AddCookiesInterceptor
import com.example.mypsychologist.core.ReceivedCookiesInterceptor
import com.example.mypsychologist.data.remote.authentication.AuthenticationService
import com.example.mypsychologist.data.remote.freeDiary.FreeDiaryService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Module
class DataModule {

    @Reusable
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideApiUrl(impl: ApiUrlProviderImpl): ApiUrlProvider = impl

//    @Reusable
//    @Provides
//    fun provideOkHttpClient(): OkHttpClient {
//        val loggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        return OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor(ReceivedCookiesInterceptor())
//            .addInterceptor(AddCookiesInterceptor())
//            .build()
//    }

    @Provides
    @Reusable
    fun provideUnsafeOkHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustAllCerts, SecureRandom())
        }

        val sslSocketFactory = sslContext.socketFactory

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ReceivedCookiesInterceptor())
            .addInterceptor(AddCookiesInterceptor())
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
            .build()
    }

    @Reusable
    @Provides
    fun provideRetrofitClient(json: Json, apiUrlProvider: ApiUrlProvider, okHttpClient: OkHttpClient): Retrofit {
       val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(apiUrlProvider.url)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(retrofit: Retrofit): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

    @Provides
    @Singleton
    fun provideFreeDiaryService(retrofit: Retrofit): FreeDiaryService {
        return retrofit.create(FreeDiaryService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideContext(application: Application): Context = application.applicationContext
}