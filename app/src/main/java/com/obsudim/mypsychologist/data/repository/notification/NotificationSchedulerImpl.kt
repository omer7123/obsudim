package com.obsudim.mypsychologist.data.repository.notification

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.obsudim.mypsychologist.domain.repository.NotificationScheduler
import com.obsudim.mypsychologist.presentation.notification.DailyNotificationWorker
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationSchedulerImpl @Inject constructor(
    private val context: Context
): NotificationScheduler{

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun schedule(time: String) {
        val delay = calculateInitialDelay(time)

        val request = OneTimeWorkRequestBuilder<DailyNotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        Log.e("Scheduler", "Next notification in ${delay / 1000}s")
        WorkManager.getInstance(context).enqueueUniqueWork(
            "daily_notification",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateInitialDelay(time: String): Long {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val targetTime = LocalTime.parse(time, formatter)

        val now = LocalDateTime.now()
        var targetDateTime = now.withHour(targetTime.hour)
            .withMinute(targetTime.minute)
            .withSecond(0)

        if (targetDateTime.isBefore(now)) {
            targetDateTime = targetDateTime.plusDays(1)
        }

        return Duration.between(now, targetDateTime).toMillis()
    }
}