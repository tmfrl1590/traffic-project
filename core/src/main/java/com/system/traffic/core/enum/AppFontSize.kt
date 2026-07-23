package com.system.traffic.core.enum

enum class AppFontSize(
    val fontSizeText: String,
    val scale: Float,
) {
    SMALL(fontSizeText = "작게", scale = 0.85f),
    MEDIUM(fontSizeText = "보통", scale = 1.0f),
    LARGE(fontSizeText = "크게", scale = 1.15f);


    companion object {
        fun fromFontSizeText(fontSizeText: String): AppFontSize {
            return entries.find { it.fontSizeText == fontSizeText } ?: MEDIUM
        }

        fun getScaleFromText(fontSizeText: String): Float {
            return fromFontSizeText(fontSizeText).scale
        }
    }
}