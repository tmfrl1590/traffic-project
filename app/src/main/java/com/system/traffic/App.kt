package com.system.traffic

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        private var instance : Application? = null

        fun context() : Context {
            return instance!!.applicationContext
        }
    }
}
