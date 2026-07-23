package com.traffic.design.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.traffic.design.R

// 1. Pretendard 폰트 패밀리 정의
val PretendardFontFamily = FontFamily(
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Normal),  // Normal 글자일 때도 Medium 매핑
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_bold, FontWeight.Bold)
)
// 2. 앱 전역 Typography 정의 (모든 텍스트 스타일 기본 폰트를 Pretendard로 지정)
val TrafficTypography = Typography(
    bodyLarge = TextStyle(fontFamily = PretendardFontFamily),
    bodyMedium = TextStyle(fontFamily = PretendardFontFamily),
    bodySmall = TextStyle(fontFamily = PretendardFontFamily),
    titleLarge = TextStyle(fontFamily = PretendardFontFamily),
    titleMedium = TextStyle(fontFamily = PretendardFontFamily),
    titleSmall = TextStyle(fontFamily = PretendardFontFamily),
    labelLarge = TextStyle(fontFamily = PretendardFontFamily),
    labelMedium = TextStyle(fontFamily = PretendardFontFamily),
    labelSmall = TextStyle(fontFamily = PretendardFontFamily)
)