package com.example.mypsychologist.di

import com.example.mypsychologist.data.repository.FeedRepositoryImpl
import com.example.mypsychologist.domain.repository.FeedRepository
import dagger.Binds
import dagger.Module

@Module
interface FeedModule {
    @Binds
    fun bindsFeedRepository(impl: FeedRepositoryImpl): FeedRepository
}