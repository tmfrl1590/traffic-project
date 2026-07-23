package com.system.traffic.core.enum

enum class AppThemeType(
    val themeName: String,
) {
    LIGHT(themeName = "라이트"),
    DARK(themeName = "다크"),
    SYSTEM(themeName = "시스템");

    fun isDarkTheme(isSystemInDark: Boolean): Boolean {
        return when (this) {
            LIGHT -> false
            DARK -> true
            SYSTEM -> isSystemInDark // 👈 휴대폰 시스템 다크모드 반영!
        }
    }

    companion object {
        fun fromThemeName(themeName: String): AppThemeType {
            return entries.firstOrNull { it.themeName == themeName } ?: SYSTEM
        }
    }
}