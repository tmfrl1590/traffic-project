package com.traffic.presentation.screens.setting.state

import com.system.traffic.core.enum.AppFontSize

data class SettingState(
    val selectedFontSize: String = AppFontSize.MEDIUM.fontSizeText
)
