package com.obsudim.mypsychologist.di

import com.obsudim.mypsychologist.data.remote.profile.UserDataSource
import com.obsudim.mypsychologist.data.remote.profile.UserDataSourceImpl
import com.obsudim.mypsychologist.data.remote.profile.UserService
import com.obsudim.mypsychologist.data.repository.ProfileRepositoryImpl
import com.obsudim.mypsychologist.data.repository.notification.NotificationContentProviderImpl
import com.obsudim.mypsychologist.data.repository.notification.NotificationSchedulerImpl
import com.obsudim.mypsychologist.data.repository.notification.NotificationSenderImpl
import com.obsudim.mypsychologist.domain.repository.NotificationContentProvider
import com.obsudim.mypsychologist.domain.repository.NotificationScheduler
import com.obsudim.mypsychologist.domain.repository.NotificationSender
import com.obsudim.mypsychologist.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProfileModule {
    @Provides
    fun provideRepository(impl: ProfileRepositoryImpl): ProfileRepository = impl

    @Provides
    fun provideNotificationScheduler(impl: NotificationSchedulerImpl): NotificationScheduler = impl

    @Provides
    fun provideNotificationSender(impl: NotificationSenderImpl): NotificationSender = impl

    @Provides
    fun provideNotificationContentProvider(impl: NotificationContentProviderImpl): NotificationContentProvider =
        impl

    @Provides
    fun provideUserDataSource(impl: UserDataSourceImpl): UserDataSource = impl

    @Provides
    fun provideUserService(@AuthRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

}