package com.example.mypsychologist.di

import com.example.mypsychologist.data.repository.DiagnosticsRepositoryImpl
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import dagger.Binds
import dagger.Module

@Module
interface DiagnosticModule {
    @Binds
    fun bindRepository(impl: DiagnosticsRepositoryImpl): DiagnosticRepository
}