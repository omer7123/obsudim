package com.obsudim.mypsychologist.domain.useCase.notificationUseCases

import com.obsudim.mypsychologist.domain.repository.NotificationScheduler
import javax.inject.Inject

class ScheduleNotificationUseCase @Inject constructor(
    private val scheduler: NotificationScheduler
) {
    suspend operator fun invoke(time: String): Unit = scheduler.schedule(time)
}