package com.system.traffic

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.system.traffic.core.enum.AppThemeType
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.presentation.screens.main.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.tan

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 세로 고정
        enableEdgeToEdge()

        requestNotificationPermission()


        setContent {
            val currentDensity = LocalDensity.current

            val isNetworkConnected by mainViewModel.isNetworkConnected.collectAsStateWithLifecycle()
            val selectedFontSize by mainViewModel.savedFontScale.collectAsStateWithLifecycle()
            val selectedTheme by mainViewModel.savedThemeType.collectAsStateWithLifecycle()

            TrafficTheme(
                darkTheme = AppThemeType.fromThemeName(selectedTheme).isDarkTheme(
                    isSystemInDark = isSystemInDarkTheme()
                )
            ) {
                CompositionLocalProvider(
                    value = LocalDensity provides Density(
                        density = currentDensity.density,
                        fontScale = selectedFontSize
                    )
                ) {
                    AppNavHost()
                }
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

