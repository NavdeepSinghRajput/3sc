package com.task.a3sctask

import android.app.Application
import android.content.Context
import com.task.a3sctask.utils.NetworkLiveData

class SC3Application : Application() {


    var appContext: Context? = null


    override fun onCreate() {
        super.onCreate()
        appContext = getApplicationContext()
        NetworkLiveData.init(this)
    }

}