package com.example.mypsychologist.di

import com.example.mypsychologist.data.repository.PsychologistRepositoryImpl
import com.example.mypsychologist.domain.repository.PsychologistRepository
import dagger.Binds
import dagger.Module

@Module
interface PsychologistModule {
    @Binds
    fun bindsPsychologistRepository(impl: PsychologistRepositoryImpl): PsychologistRepository
}