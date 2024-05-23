package com.example.mypsychologist.di

import android.app.Application
import android.content.Context
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSourceImpl
import com.example.mypsychologist.data.remote.AuthenticationDataSource
import com.example.mypsychologist.data.remote.AuthenticationDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindAuthenticationDataSource(impl: AuthenticationDataSourceImpl): AuthenticationDataSource

    @Binds
    @Singleton
    fun bindAuthenticationSharedPrefDataSource(impl: AuthenticationSharedPrefDataSourceImpl): AuthenticationSharedPrefDataSource
}