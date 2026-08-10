package com.system.traffic.presentation.screens.setting.effect

/**
 * 화면에서 실행해야 하는 일회성 사이드 이펙트 (Context 필요 작업)
 */
sealed interface SettingEffect {
    data object SendInquireEmail : SettingEffect
    data object OpenOssLicenses : SettingEffect
}
