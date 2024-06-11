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
import javax.inject.Singleton


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

    @Reusable
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ReceivedCookiesInterceptor())
            .addInterceptor(AddCookiesInterceptor())
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