package com.example.mypsychologist.di

import com.example.mypsychologist.data.repository.ExercisesRepositoryImpl
import com.example.mypsychologist.domain.repository.ExercisesRepository
import dagger.Binds
import dagger.Module

@Module
interface ExercisesModule {
    @Binds
    fun bindsRepository(impl: ExercisesRepositoryImpl): ExercisesRepository
}