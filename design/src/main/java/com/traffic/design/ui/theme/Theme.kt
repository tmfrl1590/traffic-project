package com.traffic.design.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


// 라이트 모드용 커스텀 색상 세트
val LightTrafficColors = TrafficColors(
    mainBackground = White,
    textPrimary = Black,
    textSecondary = Color(0xFF64748B),
    selectedBottomColor = SelectedBottomColor,
    unSelectedBottomColor = UnSelectedBottomColor,
    searchBarBackground = Color(0xFFF1F5F9),
    searchBarBorder = Color(0xFFE2E8F0),
    searchBarText = Color(0xFF0F172A),
    searchBarPlaceholder = Color(0xFF94A3B8),
    searchBarClearIcon = Color(0xFF64748B),
    cardBackground = Color(0xFFFFFFFF),
    cardBorder = Color(0xFFE2E8F0),
    divider = Color(0xFFF1F5F9),
    unselectedChipBackground = Color(0xFFF1F5F9),
    unselectedChipText = Color(0xFF475569),

)
// 다크 모드용 커스텀 색상 세트
val DarkTrafficColors = TrafficColors(
    mainBackground = Black,
    textPrimary = White,
    textSecondary = Color(0xFF94A3B8),
    selectedBottomColor = SelectedBottomColor,
    unSelectedBottomColor = UnSelectedBottomColor,
    searchBarBackground = Color(0xFF1E1E1E),
    searchBarBorder = Color(0xFF383838),
    searchBarText = Color(0xFFFFFFFF),
    searchBarPlaceholder = Color(0xFF8E8E93),
    searchBarClearIcon = Color(0xFFE2E8F0),
    cardBackground = Color(0xFF1E293B),
    cardBorder = Color(0xFF334155),
    divider = Color(0xFF334155),
    unselectedChipBackground = Color(0xFF334155),
    unselectedChipText = Color(0xFF94A3B8),
)


// 1. CompositionLocal 생성
private val LocalTrafficColors = staticCompositionLocalOf { LightTrafficColors }

// 2. 어디서든 TrafficTheme.colors 로 접근할 수 있는 객체 제공
object TrafficTheme {
    val colors: TrafficColors
        @Composable
        @ReadOnlyComposable
        get() = LocalTrafficColors.current
}


@Composable
fun TrafficTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val customColors = if (darkTheme) DarkTrafficColors else LightTrafficColors

    // 상단 상태바(시계, 배터리, 와이파이) 아이콘 색상 자동 제어
    val view = LocalView.current
    if (!view.isInEditMode) { // 프리뷰(Preview) 화면이 아닐 때만 실행
        SideEffect {
            val window = (view.context as Activity).window // 현재 앱이 동작하고 있는 실제 액티비티의 창(Window) 객체를 구함 -> 상단 상태바의 배경 투명도, 와이파이/배터리 아이콘 색상은 전부 액티비티의 Window가 총괄
            // 다크모드일 때(!darkTheme == false) ➔ 상태바 아이콘이 흰색으로 바뀜
            // 라이트모드일 때(!darkTheme == true) ➔ 상태바 아이콘이 검은색으로 바뀜
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        value = LocalTrafficColors provides customColors,
    ){
        MaterialTheme(
            typography = TrafficTypography,
            content = content
        )
    }
}