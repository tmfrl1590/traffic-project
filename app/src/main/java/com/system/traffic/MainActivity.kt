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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.system.traffic.core.enum.AppThemeType
import com.traffic.design.ui.theme.MainColor
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.presentation.screens.main.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                    ) {
                        AnimatedVisibility(
                            visible = !isNetworkConnected,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            NetworkOfflineBanner()
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            AppNavHost()
                        }
                    }
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


@Composable
private fun NetworkOfflineBanner() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
        ,
        color = MainColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
            ,
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "네트워크 연결이 원활하지 않습니다.",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}