package com.system.traffic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.ads.MobileAds
import com.system.traffic.navigation.AppNavHost
import com.system.traffic.ui.theme.TrafficTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)

        setContent {
            TrafficTheme {
                AppNavHost()
            }
        }
    }
}