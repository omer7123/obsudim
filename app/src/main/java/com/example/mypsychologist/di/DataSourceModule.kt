package com.example.mypsychologist.di

import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSourceImpl
import com.example.mypsychologist.data.remote.authentication.AuthenticationDataSource
import com.example.mypsychologist.data.remote.authentication.AuthenticationDataSourceImpl
import com.example.mypsychologist.data.remote.diagnostic.TestsDiagnosticDataSource
import com.example.mypsychologist.data.remote.diagnostic.TestsDiagnosticDataSourceImpl
import com.example.mypsychologist.data.remote.education.EducationDataSource
import com.example.mypsychologist.data.remote.education.EducationDataSourceImpl
import com.example.mypsychologist.data.remote.exercises.ExerciseDataSource
import com.example.mypsychologist.data.remote.exercises.ExerciseDataSourceImpl
import com.example.mypsychologist.data.remote.freeDiary.FreeDiaryDataSource
import com.example.mypsychologist.data.remote.freeDiary.FreeDiaryDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindAuthenticationDataSource(impl: AuthenticationDataSourceImpl): AuthenticationDataSource

    @Binds
    @Singleton
    fun bindAuthenticationSharedPrefDataSource(impl: AuthenticationSharedPrefDataSourceImpl): AuthenticationSharedPrefDataSource

    @Binds
    @Singleton
    fun bindFreeDiaryDataSource(impl: FreeDiaryDataSourceImpl): FreeDiaryDataSource

    @Binds
    @Singleton
    fun bindTestsDiagnosticDataSource(impl: TestsDiagnosticDataSourceImpl): TestsDiagnosticDataSource

    @Binds
    @Singleton
    fun bindEducationDataSource(impl: EducationDataSourceImpl): EducationDataSource

    @Binds
    @Singleton
    fun bindExerciseDataSource(impl: ExerciseDataSourceImpl): ExerciseDataSource
}