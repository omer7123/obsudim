package com.example.mypsychologist.di

import com.example.mypsychologist.data.repository.ProfileRepositoryImpl
import com.example.mypsychologist.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module

@Module
interface ProfileModule {
    @Binds
    fun bindsRepository(impl: ProfileRepositoryImpl): ProfileRepository
}