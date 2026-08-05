package com.system.traffic.presentation.screens.setting.state

import com.system.traffic.core.enums.AppFontSize
import com.system.traffic.core.enums.AppThemeType

data class SettingState(
    val selectedFontSize: String = AppFontSize.MEDIUM.fontSizeText,
    val selectedTheme: String = AppThemeType.LIGHT.themeName,
    val isShowResetConfirmDialog: Boolean = false,
)
