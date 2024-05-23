package com.example.mypsychologist.di

import android.app.Application
import android.content.Context
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSourceImpl
import com.example.mypsychologist.data.remote.AuthenticationDataSource
import com.example.mypsychologist.data.remote.AuthenticationDataSourceImpl
import com.example.mypsychologist.data.repository.AuthenticationRepositoryImpl
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
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
    fun provideRetrofitClient(json: Json, apiUrlProvider: ApiUrlProvider): Retrofit =
        Retrofit.Builder()
            .baseUrl(apiUrlProvider.url)
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext
}