package com.system.traffic

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.system.traffic.notification.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(){
    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        notificationHelper.createNotificationChannel()
        setupGlobalExceptionHandler()
    }

    private fun setupGlobalExceptionHandler() {
        // 디버그 빌드는 리포트 수집 제외 (개발 중 크래시가 콘솔 오염 방지)
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG

        // 이 시점의 기본 핸들러 = Crashlytics 핸들러 (초기화가 onCreate보다 먼저 일어남)
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            Log.e(TAG, "Uncaught exception in ${thread.name}", e)
            // 반드시 위임해야 Crashlytics 리포트가 유실되지 않음
            defaultHandler?.uncaughtException(thread, e)
        }
    }


    companion object {
        private const val TAG = "App GlobalExceptionHandler"
    }
}
