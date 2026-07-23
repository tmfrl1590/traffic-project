package com.traffic.design.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


// 라이트 모드용 커스텀 색상 세트
val LightTrafficColors = TrafficColors(
    mainBackground = White,
    textPrimary = Black,
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
    content: @Composable () -> Unit
) {
    // darkTheme 여부에 따라 내 커스텀 색상표 교체!
    val customColors = if (darkTheme) DarkTrafficColors else LightTrafficColors
    CompositionLocalProvider(
        LocalTrafficColors provides customColors,
        content = content
    )
}