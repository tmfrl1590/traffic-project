package com.system.traffic.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.system.traffic.MainActivity
import com.system.traffic.design.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {

    private val notificationManager =
        context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    fun buildNotification(title: String, body: String): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.main_bus)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(createPendingIntent(requestCode = 0))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(0xFF3B82F6.toInt())
            .build()
    }

    fun notify(notification: Notification): Int {
        val id = System.currentTimeMillis().toInt()
        notificationManager.notify(id, notification)
        return id
    }

    fun createNotificationChannel() {
        val trafficChannel = NotificationChannel(
            CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = NOTIFICATION_CHANNEL_DESCRIPTION
        }
        notificationManager.createNotificationChannel(trafficChannel)
    }

    private fun createPendingIntent(requestCode: Int): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        return PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        private const val CHANNEL_ID = "traffic_notification_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Traffic Notifications"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "Notifications for Traffic App"
    }
}