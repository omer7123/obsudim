package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.data.remote.tags.TagsDataSource
import com.obsudim.mypsychologist.data.remote.tags.TagsDataSourceImpl
import com.obsudim.mypsychologist.data.remote.tags.TagsService
import com.obsudim.mypsychologist.data.repository.TagsRepositoryImpl
import com.obsudim.mypsychologist.domain.repository.TagsRepository
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
    fun provideService(@AuthRetrofit retrofit: Retrofit): TagsService =
        retrofit.create(TagsService::class.java)
}