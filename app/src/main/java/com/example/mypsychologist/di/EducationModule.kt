package com.example.mypsychologist.di

import com.example.mypsychologist.data.repository.EducationRepositoryImpl
import com.example.mypsychologist.domain.repository.EducationRepository
import dagger.Binds
import dagger.Module

@Module
interface EducationModule {
    @Binds
    fun bindsEducationRepository(impl: EducationRepositoryImpl): EducationRepository
}