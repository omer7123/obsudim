package com.example.mypsychologist.di

import androidx.lifecycle.ViewModel
import com.example.mypsychologist.data.repository.AuthenticationRepositoryImpl
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import com.example.mypsychologist.presentation.authentication.authFragment.AuthViewModel
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterViewModel
import com.example.mypsychologist.presentation.main.mainFragment.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    @[IntoMap ClassKey(RegisterViewModel::class)]
    fun provideRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(AuthViewModel::class)]
    fun provideAuthViewModel(authViewModel: AuthViewModel): ViewModel




}