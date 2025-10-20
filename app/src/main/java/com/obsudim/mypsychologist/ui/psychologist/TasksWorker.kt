package com.obsudim.mypsychologist.ui.psychologist

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kirich1409.androidnotificationdsl.notification
import com.obsudim.mypsychologist.MainActivity
import com.obsudim.mypsychologist.MainActivity.Companion.TASK_CHANNEL_ID
import com.obsudim.mypsychologist.R

class TasksWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val notification = notification(
        context,
        TASK_CHANNEL_ID,
        smallIcon = R.drawable.ic_cognition
    ) {
        contentTitle = context.getString(R.string.assignments)

        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.fragment_psychologists_with_tasks)
            .createPendingIntent()

        actions {
            action(
                title = context.getString(R.string.go),
                intent = pendingIntent
            )
        }
    }


    override fun doWork(): Result {

        val notificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE)

        (notificationManager as NotificationManager).notify(TASK_NOTIFICATION_ID, notification)

        return Result.success()
    }

    companion object {
        const val TASK_NOTIFICATION_ID = 1
    }
}