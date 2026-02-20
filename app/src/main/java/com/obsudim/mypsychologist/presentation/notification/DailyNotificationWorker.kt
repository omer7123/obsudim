package com.obsudim.mypsychologist.presentation.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.repository.notification.DefaultTime
import com.obsudim.mypsychologist.domain.repository.NotificationContentProvider
import com.obsudim.mypsychologist.domain.repository.NotificationScheduler
import com.obsudim.mypsychologist.domain.repository.NotificationSender
import com.obsudim.mypsychologist.domain.useCase.notificationUseCases.GetNotificationTimeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

class DailyNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val sender: NotificationSender,
    private val scheduler: NotificationScheduler,
    private val getNotificationTimeUseCase: GetNotificationTimeUseCase,
    private val getNotificationContent: NotificationContentProvider,
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        val content = getNotificationContent.getNotification()

        sender.send(
            title = content.title,
            text = content.text
        )

        val actualTime = try {
            getNotificationTimeUseCase().first { it is Resource.Success }
                .let { (it as Resource.Success).data }
        } catch (e: Exception) {
            DefaultTime.DEFAULT_TIME
        }

        scheduler.schedule(actualTime)
        return Result.success()
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): DailyNotificationWorker
    }
}