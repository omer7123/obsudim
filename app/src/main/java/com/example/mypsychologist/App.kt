package com.example.mypsychologist

import android.app.Application
import com.example.mypsychologist.di.AppComponent
import com.example.mypsychologist.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}