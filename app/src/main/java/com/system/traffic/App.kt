package com.system.traffic

import android.app.Application
import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.traffic.common.CommonConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)

        CommonConfig.initialize(
            adUnitId = BuildConfig.AD_UNIT_ID
        )
    }

    companion object {
        private var instance : Application? = null

        fun context() : Context {
            return instance!!.applicationContext
        }
    }
}
