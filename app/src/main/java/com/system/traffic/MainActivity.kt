package com.system.traffic

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.presentation.screens.main.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 세로 고정
        enableEdgeToEdge()

        requestNotificationPermission()

        setContent {
            TrafficTheme {
                AppNavHost()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}

