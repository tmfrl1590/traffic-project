package com.traffic.presentation.screens.setting.state

import com.system.traffic.core.enum.AppFontSize
import com.system.traffic.core.enum.AppThemeType

data class SettingState(
    val selectedFontSize: String = AppFontSize.MEDIUM.fontSizeText,
    val selectedTheme: String = AppThemeType.LIGHT.themeName,
)
