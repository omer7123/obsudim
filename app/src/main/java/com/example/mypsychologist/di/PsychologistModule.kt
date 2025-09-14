package com.example.mypsychologist.di

import com.example.mypsychologist.data.remote.psychologist.PsychologistDataSource
import com.example.mypsychologist.data.remote.psychologist.PsychologistDataSourceImpl
import com.example.mypsychologist.data.remote.psychologist.PsychologistService
import com.example.mypsychologist.data.repository.PsychologistRepositoryImpl
import com.example.mypsychologist.domain.repository.retrofit.PsychologistRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PsychologistModule {
    @Provides
    fun providesPsychologistRepository(impl: PsychologistRepositoryImpl): PsychologistRepository =
        impl

    @Provides
    fun providesPsychologistService(@AuthRetrofit retrofit: Retrofit): PsychologistService =
        retrofit.create(PsychologistService::class.java)

    @Provides
    fun providesPsychologistDataSource(impl: PsychologistDataSourceImpl): PsychologistDataSource =
        impl
}