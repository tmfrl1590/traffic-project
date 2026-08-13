package com.system.traffic.presentation.screens.setting.state

import com.system.traffic.core.enums.AppFontSize
import com.system.traffic.core.enums.AppThemeType

data class SettingState(
    val selectedFontSize: AppFontSize = AppFontSize.MEDIUM,
    val selectedTheme: AppThemeType = AppThemeType.LIGHT,
    val isShowResetConfirmDialog: Boolean = false,
)
