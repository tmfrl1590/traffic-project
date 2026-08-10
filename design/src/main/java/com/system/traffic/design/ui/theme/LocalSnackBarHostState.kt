package com.system.traffic.design.ui.theme

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackBarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error("LocalSnackbarHostState가 제공되지 않았습니다. TrafficTheme 내부에서 호출되었는지 확인하세요.")
}

