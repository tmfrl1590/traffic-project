package com.system.traffic.design.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.system.traffic.design.R

// 1. Pretendard 폰트 패밀리 정의
val PretendardFontFamily = FontFamily(
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Normal),  // Normal 글자일 때도 Medium 매핑
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_bold, FontWeight.Bold)
)
// 2. 앱 전역 Typography 정의
data class TrafficTypography(
    val title: TextStyle,
    val sectionTitle: TextStyle,
    val sectionBody1: TextStyle,
    val sectionBody2: TextStyle,
    val button: TextStyle,
    val dialogTitle: TextStyle,
    val dialogDescription: TextStyle,
    val cardTitle: TextStyle,
    val cardBody: TextStyle,
    val placeHolder: TextStyle,
    val chip: TextStyle,
    val bottomTab: TextStyle,
    val empty: TextStyle,

    val busArriveTitle: TextStyle,
    val busArriveBody: TextStyle,
    val lineName: TextStyle,
)

val DefaultTypography = TrafficTypography(
    title = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
    ),
    sectionTitle = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    sectionBody1 = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
    ),
    sectionBody2 = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 12.sp,
    ),
    button = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 14.sp,
    ),
    dialogTitle = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    dialogDescription = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 14.sp,
    ),
    placeHolder = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 14.sp,
    ),
    chip = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    ),
    empty = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 20.sp,
    ),
    cardTitle = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    cardBody = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 14.sp,
    ),
    bottomTab = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 14.sp,
    ),
    busArriveTitle = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    busArriveBody = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 14.sp,
    ),
    lineName = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    )
)



val LocalTrafficTypography = staticCompositionLocalOf { DefaultTypography }