package com.obsudim.mypsychologist.di

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module

@Module
abstract class WorkerModule {

    @Binds
    abstract fun bindWorkerFactory(
        factory: DailyNotificationWorkerFactory
    ): WorkerFactory
}