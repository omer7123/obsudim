package com.example.mypsychologist.di

import com.example.mypsychologist.data.remote.exercises.BeliefService
import com.example.mypsychologist.data.remote.exercises.BeliefsDataSource
import com.example.mypsychologist.data.remote.exercises.BeliefsDataSourceImpl
import com.example.mypsychologist.data.remote.exercises.DiaryDataSource
import com.example.mypsychologist.data.remote.exercises.DiaryDataSourceImpl
import com.example.mypsychologist.data.remote.exercises.DiaryService
import com.example.mypsychologist.data.remote.exercises.ProblemDataSource
import com.example.mypsychologist.data.remote.exercises.ProblemDataSourceImpl
import com.example.mypsychologist.data.remote.exercises.ProblemService
import com.example.mypsychologist.data.repository.CbtRepositoryImpl
import com.example.mypsychologist.domain.repository.retrofit.CbtRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ExercisesModule {

    @Provides
    fun bindsCbtRepository(impl: CbtRepositoryImpl): CbtRepository = impl

    @Provides
    fun providesProblemService(retrofit: Retrofit): ProblemService =
        retrofit.create(ProblemService::class.java)

    @Provides
    fun providesDiaryService(retrofit: Retrofit): DiaryService =
        retrofit.create(DiaryService::class.java)

    @Provides
    fun providesBeliefService(retrofit: Retrofit): BeliefService =
        retrofit.create(BeliefService::class.java)

    @Provides
    fun provideBeliefsDataSource(impl: BeliefsDataSourceImpl): BeliefsDataSource = impl

    @Provides
    fun provideDiaryDataSource(impl: DiaryDataSourceImpl): DiaryDataSource = impl

    @Provides
    fun provideProblemDataSource(impl: ProblemDataSourceImpl): ProblemDataSource = impl
}