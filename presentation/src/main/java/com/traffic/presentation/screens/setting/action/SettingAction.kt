package com.traffic.presentation.screens.setting.action

sealed interface SettingAction {
    data object OnClickInquire: SettingAction
    data object OnClickOpenSource: SettingAction
}