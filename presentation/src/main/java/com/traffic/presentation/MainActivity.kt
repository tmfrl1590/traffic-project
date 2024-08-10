package com.traffic.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.traffic.presentation.navigation.AppNavHost
import com.traffic.presentation.ui.theme.TrafficTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //MobileAds.initialize(this)

        setContent {
            TrafficTheme {
                AppNavHost()
            }
        }
    }
}