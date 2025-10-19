package com.obsudim.mypsychologist

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.obsudim.mypsychologist.di.AppComponent
import com.obsudim.mypsychologist.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}