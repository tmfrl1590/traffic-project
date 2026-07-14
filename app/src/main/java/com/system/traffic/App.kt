package com.system.traffic

import android.app.Application
import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.android.gms.ads.MobileAds
import com.traffic.design.CommonConfig
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

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "traffic_notification_channel"
            val channelName = "Traffic Notifications"
            val channelDescription = "Notifications for Traffic App"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private var instance : Application? = null

        fun context() : Context {
            return instance!!.applicationContext
        }
    }
}
