package com.system.traffic

import android.content.pm.ActivityInfo
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.system.traffic.permission.PermissionManager
import com.system.traffic.design.R
import com.system.traffic.design.component.AdConfig
import com.system.traffic.design.ui.theme.MainColor
import com.system.traffic.design.ui.theme.TrafficTheme
import com.system.traffic.navigation.TrafficNavigationRoot
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val rootViewModel: RootViewModel by viewModels()

    @Inject
    lateinit var adConfig: AdConfig

    @Inject
    lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen() // super.onCreate 이전
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 세로 고정
        enableEdgeToEdge()

        permissionManager.requestNotificationPermission { isGranted ->
            if (!isGranted) {
                // 권한 거부 시 처리
            }
        }

        // 테마 로드 전까지 시스템 스플래시 유지
        splashScreen.setKeepOnScreenCondition { rootViewModel.appConfig.value == null }

        setContent {
            val isNetworkConnected by rootViewModel.isNetworkConnected.collectAsStateWithLifecycle()
            val appConfig by rootViewModel.appConfig.collectAsStateWithLifecycle()
            val config = appConfig ?: return@setContent // 로드 전엔 그리지 않음

            val isDarkTheme = config.themeType.isDarkTheme(isSystemInDarkTheme())

            TrafficTheme(
                adConfig = adConfig,
                isDarkTheme = isDarkTheme,
                selectedFontSize = config.fontScale,
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        AnimatedVisibility(
                            visible = !isNetworkConnected,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            NetworkOfflineBanner()
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            TrafficNavigationRoot()
                        }
                    }
                }
            )
        }
    }
}


@Composable
private fun NetworkOfflineBanner() {
    Surface(
        modifier = Modifier
            .statusBarsPadding()
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
                text = stringResource(R.string.network_offline),
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}