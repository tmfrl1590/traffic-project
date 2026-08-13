package com.system.traffic.presentation.screens.setting.action

import com.system.traffic.core.enums.AppFontSize
import com.system.traffic.core.enums.AppThemeType

sealed interface SettingAction {
    data object OnClickInquire: SettingAction
    data object OnClickOpenSource: SettingAction
    data class OnClickFontSize(val fontSize: AppFontSize): SettingAction
    data class OnClickTheme(val themeType: AppThemeType): SettingAction
    data object OnClickReset: SettingAction
    data object OnDismissResetDialog: SettingAction
    data object OnClickResetConfirm: SettingAction
}