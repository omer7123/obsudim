package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.data.remote.profile.UserDataSource
import com.obsudim.mypsychologist.data.remote.profile.UserDataSourceImpl
import com.obsudim.mypsychologist.data.remote.profile.UserService
import com.obsudim.mypsychologist.data.repository.ProfileRepositoryImpl
import com.obsudim.mypsychologist.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProfileModule {
    @Provides
    fun provideRepository(impl: ProfileRepositoryImpl): ProfileRepository = impl

    @Provides
    fun provideUserDataSource(impl: UserDataSourceImpl): UserDataSource = impl

    @Provides
    fun provideUserService(@AuthRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

}