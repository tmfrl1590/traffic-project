package com.system.traffic.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.system.traffic.BuildConfig
import com.system.traffic.design.R
import com.system.traffic.notification.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class TrafficFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onRegistered(installationId: String) {
        super.onRegistered(installationId)
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Refreshed installationId: $installationId")
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: ${remoteMessage.from}")

        val title = remoteMessage.notification?.title ?: remoteMessage.data["title"] ?: getString(R.string.app_name)
        val body = remoteMessage.notification?.body ?: remoteMessage.data["body"] ?: ""

        val notification = notificationHelper.buildNotification(
            title = title,
            body = body,
        )

        notificationHelper.notify(notification = notification)
    }

    companion object {
        private const val TAG = "TrafficFCM"
    }
}
