package com.example.mypsychologist.di

import com.example.mypsychologist.data.repository.CbtRepositoryImpl
import com.example.mypsychologist.data.repository.RebtRepositoryImpl
import com.example.mypsychologist.domain.repository.CbtRepository
import com.example.mypsychologist.domain.repository.RebtRepository
import dagger.Binds
import dagger.Module

@Module
interface ExercisesModule {
    @Binds
    fun bindsRebtRepository(impl: RebtRepositoryImpl): RebtRepository

    @Binds
    fun bindsCbtRepository(impl: CbtRepositoryImpl): CbtRepository
}