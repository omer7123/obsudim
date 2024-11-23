package com.example.mypsychologist

import android.app.Application
import com.example.mypsychologist.di.AppComponent
import com.example.mypsychologist.di.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}