package com.system.traffic.design.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val MainColor = Color(0xFF3B82F6)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val SelectedBottomColor = Color(0xFF3B82F6)
val UnSelectedBottomColor = Color(0xFF6D7680)

data class TrafficColors(
    // 배경 (Backgrounds)
    val mainBackground: Color,

    // 텍스트
    val textPrimary: Color,      // 주요 타이틀 및 강한 글자색
    val textSecondary: Color,

    // 하단 네비게이션바 색상
    val selectedBottomColor: Color,
    val unSelectedBottomColor: Color,

    // 검색바 색상
    val searchBarBackground: Color,
    val searchBarBorder: Color,
    val searchBarText: Color,
    val searchBarPlaceholder: Color,
    val searchBarClearIcon: Color,

    // 카드
    val cardBackground: Color,
    val cardBorder: Color,

    // 구분선
    val divider: Color,

    // 칩
    val unselectedChipBackground: Color,
    val unselectedChipText: Color,
)


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


val LocalTrafficColors = staticCompositionLocalOf { LightTrafficColors }