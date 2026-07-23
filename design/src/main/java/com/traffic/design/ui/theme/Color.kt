package com.traffic.design.ui.theme

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

