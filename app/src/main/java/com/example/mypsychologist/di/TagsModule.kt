package com.example.mypsychologist.di

import com.example.mypsychologist.data.remote.tags.TagsDataSource
import com.example.mypsychologist.data.remote.tags.TagsDataSourceImpl
import com.example.mypsychologist.data.remote.tags.TagsService
import com.example.mypsychologist.data.repository.TagsRepositoryImpl
import com.example.mypsychologist.domain.repository.TagsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class TagsModule {

    @Provides
    fun provideRepository(impl: TagsRepositoryImpl): TagsRepository = impl

    @Provides
    fun provideDataSource(impl: TagsDataSourceImpl): TagsDataSource = impl

    @Provides
    fun provideService(retrofit: Retrofit): TagsService =
        retrofit.create(TagsService::class.java)
}