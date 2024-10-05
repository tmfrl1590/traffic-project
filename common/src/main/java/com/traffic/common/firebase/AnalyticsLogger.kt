package com.traffic.common.firebase

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

fun logEvent(context: Context, screenName: String) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
        param("screen_name", screenName)
    }
}