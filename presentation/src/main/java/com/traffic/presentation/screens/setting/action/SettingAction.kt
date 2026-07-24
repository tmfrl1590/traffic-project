package com.traffic.presentation.screens.setting.action

sealed interface SettingAction {
    data object OnClickInquire: SettingAction
    data object OnClickOpenSource: SettingAction
    data class OnClickFontSize(val fontSizeText: String): SettingAction
    data class OnClickTheme(val themeType: String): SettingAction
    data object OnClickReset: SettingAction
    data object OnDismissResetDialog: SettingAction
    data object OnClickResetConfirm: SettingAction
}