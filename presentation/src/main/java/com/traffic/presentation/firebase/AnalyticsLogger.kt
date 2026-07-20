package com.traffic.presentation.firebase

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.traffic.presentation.BuildConfig

fun logEvent(context: Context, screenName: String) {
    if (BuildConfig.DEBUG) return
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
        param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        param(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
    }
}

@Composable
fun TrackScreenView(screenName: ScreenName) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        logEvent(context, screenName.value)
    }
}

sealed class ScreenName(val value: String) {
    data object Splash: ScreenName(value = "SplashScreen")
    data object Home: ScreenName(value = "HomeScreen")
    data object Station: ScreenName(value = "StationScreen")
    data object BusArrive: ScreenName(value = "BusArriveScreen")
    data object Setting: ScreenName(value = "SettingScreen")
}