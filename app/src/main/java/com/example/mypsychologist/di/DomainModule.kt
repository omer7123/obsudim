package com.example.mypsychologist.di

import com.example.mypsychologist.data.repository.AuthenticationRepositoryImpl
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository

}