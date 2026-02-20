package com.obsudim.mypsychologist.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.obsudim.mypsychologist.presentation.notification.DailyNotificationWorker
import javax.inject.Inject

class DailyNotificationWorkerFactory @Inject constructor(
    private val dailyNotificationWorkerFactory: DailyNotificationWorker.Factory
) : WorkerFactory() {


    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            DailyNotificationWorker::class.java.name -> dailyNotificationWorkerFactory.create(
                appContext,
                workerParameters
            )

            else -> null
        }
    }
}