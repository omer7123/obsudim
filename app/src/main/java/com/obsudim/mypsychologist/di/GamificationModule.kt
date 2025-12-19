package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.data.remote.gamification.GamificationDataSource
import com.obsudim.mypsychologist.data.remote.gamification.GamificationDataSourceImpl
import com.obsudim.mypsychologist.data.remote.gamification.GamificationService
import com.obsudim.mypsychologist.data.repository.GamificationRepositoryImpl
import com.obsudim.mypsychologist.domain.repository.retrofit.GamificationRepository
import dagger.Module
import dagger.Provides

@Module
class GamificationModule {

    @Provides
    fun provideGamificationDataSource(
        service: GamificationService
    ): GamificationDataSource {
        return GamificationDataSourceImpl(service)
    }

    @Provides
    fun provideGamificationRepository(
        dataSource: GamificationDataSource
    ): GamificationRepository {
        return GamificationRepositoryImpl(dataSource)
    }
}