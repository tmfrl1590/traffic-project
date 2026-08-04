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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.system.traffic.core.enums.AppThemeType
import com.system.traffic.permission.PermissionManager
import com.traffic.design.component.AdConfig
import com.traffic.design.ui.theme.MainColor
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.navigation.TrafficNavigationRoot
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
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 세로 고정
        enableEdgeToEdge()

        permissionManager.requestNotificationPermission { isGranted ->
            if (!isGranted) {
                // 권한 거부 시 처리
            }
        }

        setContent {
            val isNetworkConnected by rootViewModel.isNetworkConnected.collectAsStateWithLifecycle()
            val selectedFontSize by rootViewModel.savedFontScale.collectAsStateWithLifecycle()
            val selectedTheme by rootViewModel.savedThemeType.collectAsStateWithLifecycle()

            val isDarkTheme = AppThemeType.fromThemeName(selectedTheme).isDarkTheme(
                isSystemInDark = isSystemInDarkTheme()
            )

            TrafficTheme(
                adConfig = adConfig,
                isDarkTheme = isDarkTheme,
                selectedFontSize = selectedFontSize,
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
                text = "네트워크 연결이 원활하지 않습니다.",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}