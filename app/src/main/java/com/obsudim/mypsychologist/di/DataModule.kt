package com.obsudim.mypsychologist.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.obsudim.mypsychologist.core.AddCookiesInterceptor
import com.obsudim.mypsychologist.core.AuthInterceptor
import com.obsudim.mypsychologist.core.ReceivedCookiesInterceptor
import com.obsudim.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.obsudim.mypsychologist.data.remote.authentication.AuthenticationDataSource
import com.obsudim.mypsychologist.data.remote.authentication.AuthenticationService
import com.obsudim.mypsychologist.data.remote.diagnostic.TestsDiagnosticService
import com.obsudim.mypsychologist.data.remote.education.EducationService
import com.obsudim.mypsychologist.data.remote.exercises.ExerciseService
import com.obsudim.mypsychologist.data.remote.freeDiary.FreeDiaryService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

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
    @BaseOkHttpClient
    fun provideBaseOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ReceivedCookiesInterceptor())
            .addInterceptor(AddCookiesInterceptor())
            .build()
    }

    @Singleton
    @Provides
    @AuthOkHttpClient
    fun provideAuthOkHttpClient(
        @BaseOkHttpClient baseOkHttpClient: OkHttpClient,
        authInterceptorProvider: Provider<AuthInterceptor>
    ): OkHttpClient {
        return baseOkHttpClient.newBuilder()
            // сначала авторизация
            .addInterceptor(authInterceptorProvider.get())
            // потом логирование (чтобы логировать уже корректные запросы)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Reusable
    @Provides
    @BaseRetrofit
    fun provideBaseRetrofitClient(
        json: Json,
        apiUrlProvider: ApiUrlProvider,
        @BaseOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(apiUrlProvider.url)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Singleton
    @Provides
    @AuthRetrofit
    fun provideAuthRetrofitClient(
        json: Json,
        apiUrlProvider: ApiUrlProvider,
        @AuthOkHttpClient authOkHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(apiUrlProvider.url)
            .client(authOkHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        authApi: AuthenticationDataSource,
        sharedPrefDataSource: AuthenticationSharedPrefDataSource
    ): AuthInterceptor {
        return AuthInterceptor(authApi, sharedPrefDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(
        @BaseRetrofit retrofit: Retrofit // Используем базовый Retrofit
    ): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

    @Provides
    @Singleton
    fun provideFreeDiaryService(
        @AuthRetrofit retrofit: Retrofit // Используем Retrofit с AuthInterceptor
    ): FreeDiaryService {
        return retrofit.create(FreeDiaryService::class.java)
    }

    @Provides
    @Singleton
    fun provideTestsDiagnosticService(
        @AuthRetrofit retrofit: Retrofit
    ): TestsDiagnosticService {
        return retrofit.create(TestsDiagnosticService::class.java)
    }

    @Provides
    @Singleton
    fun provideEducationService(
        @AuthRetrofit retrofit: Retrofit
    ): EducationService {
        return retrofit.create(EducationService::class.java)
    }

    @Provides
    @Singleton
    fun provideExerciseService(
        @AuthRetrofit retrofit: Retrofit
    ): ExerciseService {
        return retrofit.create(ExerciseService::class.java)
    }

}