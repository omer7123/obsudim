package com.obsudim.mypsychologist.data.repository.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.domain.repository.NotificationSender
import javax.inject.Inject

class NotificationSenderImpl @Inject constructor(
    private val context: Context
): NotificationSender {

    override fun send(title: String, text: String) {
        val channelId = "daily_channel"

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Daily Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_app)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .build()

        manager.notify(1001, notification)
    }
}