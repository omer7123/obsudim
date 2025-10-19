package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.data.remote.exercises.ProblemDataSource
import com.obsudim.mypsychologist.data.remote.exercises.ProblemDataSourceImpl
import com.obsudim.mypsychologist.data.remote.exercises.ProblemService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ExercisesModule {
    @Provides
    fun providesProblemService(@AuthRetrofit retrofit: Retrofit): ProblemService =
        retrofit.create(ProblemService::class.java)

    @Provides
    fun provideProblemDataSource(impl: ProblemDataSourceImpl): ProblemDataSource = impl
}