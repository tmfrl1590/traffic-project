package com.traffic.design.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowCompat
import com.traffic.design.component.AdConfig
import com.traffic.design.component.LocalAdConfig

// 어디서든 접근할 수 있는 객체 제공
object TrafficTheme {
    val colors: TrafficColors
        @Composable @ReadOnlyComposable
        get() = LocalTrafficColors.current

    val typography: TrafficTypography
        @Composable @ReadOnlyComposable
        get() = LocalTrafficTypography.current
}

@Composable
fun TrafficTheme(
    isDarkTheme: Boolean,
    selectedFontSize: Float,
    adConfig: AdConfig = AdConfig(),
    colors: TrafficColors = if (isDarkTheme) DarkTrafficColors else LightTrafficColors,
    typography: TrafficTypography = DefaultTypography,
    content: @Composable () -> Unit,
) {
    val currentDensity = LocalDensity.current

    // 상단 상태바(시계, 배터리, 와이파이) 아이콘 색상 자동 제어
    val view = LocalView.current
    if (!view.isInEditMode) { // 프리뷰(Preview) 화면이 아닐 때만 실행
        SideEffect {
            val window = (view.context as Activity).window // 현재 앱이 동작하고 있는 실제 액티비티의 창(Window) 객체를 구함 -> 상단 상태바의 배경 투명도, 와이파이/배터리 아이콘 색상은 전부 액티비티의 Window가 총괄
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
        }
    }

    CompositionLocalProvider(
        LocalAdConfig provides adConfig,
        LocalTrafficColors provides colors,
        LocalTrafficTypography provides typography,
        LocalDensity provides Density(
            density = currentDensity.density,
            fontScale = selectedFontSize,
        ),
    ){
        content()
    }
}