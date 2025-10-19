package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.data.remote.psychologist.PsychologistDataSource
import com.obsudim.mypsychologist.data.remote.psychologist.PsychologistDataSourceImpl
import com.obsudim.mypsychologist.data.remote.psychologist.PsychologistService
import com.obsudim.mypsychologist.data.repository.PsychologistRepositoryImpl
import com.obsudim.mypsychologist.domain.repository.retrofit.PsychologistRepository
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