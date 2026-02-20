package com.obsudim.mypsychologist

import android.app.Application
import androidx.work.Configuration
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen
import com.obsudim.mypsychologist.di.AppComponent
import com.obsudim.mypsychologist.di.DaggerAppComponent
import com.obsudim.mypsychologist.di.DailyNotificationWorkerFactory
import javax.inject.Inject

class App : Application(), Configuration.Provider {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerFactory: DailyNotificationWorkerFactory

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AndroidThreeTen.init(this)
        appComponent = DaggerAppComponent.factory().create(applicationContext)

        appComponent.inject(this)


    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}