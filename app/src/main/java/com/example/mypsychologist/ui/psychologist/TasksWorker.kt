package com.example.mypsychologist.ui.psychologist

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mypsychologist.MainActivity
import com.example.mypsychologist.MainActivity.Companion.TASK_CHANNEL_ID
import com.example.mypsychologist.R
import com.example.mypsychologist.data.repository.PsychologistRepositoryImpl
import com.example.mypsychologist.di.AppModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kirich1409.androidnotificationdsl.notification

class TasksWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val notification = notification(
        context,
        TASK_CHANNEL_ID,
        smallIcon = R.drawable.ic_cognition
    ) {
        contentTitle = context.getString(R.string.tasks)

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

    private val taskListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE)

            (notificationManager as NotificationManager).notify(TASK_NOTIFICATION_ID, notification)
        }

        override fun onCancelled(error: DatabaseError) {}
    }

    override fun doWork(): Result {
        val auth = FirebaseAuth.getInstance()

        Firebase.database(AppModule.URL).reference.child(auth.currentUser?.uid.toString())
            .child(PsychologistRepositoryImpl.TASKS)
            .addValueEventListener(taskListener)


        return Result.success()
    }

    companion object {
        private const val TASK_NOTIFICATION_ID = 1
    }
}