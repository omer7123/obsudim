package com.obsudim.mypsychologist.extensions

import android.content.Context
import android.widget.Toast
import com.obsudim.mypsychologist.App
import com.obsudim.mypsychologist.di.AppComponent

fun Context.showToast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.getAppComponent(): AppComponent = (this.applicationContext as App).appComponent
