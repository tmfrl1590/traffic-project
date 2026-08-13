package com.system.traffic.core.enums

enum class AppThemeType {
    LIGHT,
    DARK,
    SYSTEM;

    fun isDarkTheme(isSystemInDark: Boolean): Boolean {
        return when (this) {
            LIGHT -> false
            DARK -> true
            SYSTEM -> isSystemInDark // 👈 휴대폰 시스템 다크모드 반영!
        }
    }
}
