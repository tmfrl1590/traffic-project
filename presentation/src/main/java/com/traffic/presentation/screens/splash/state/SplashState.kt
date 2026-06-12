package com.traffic.presentation.screens.splash.state

data class SplashState(
    val isLoading: Boolean = false,
    val message: String = "",
    val progress: Float = 0f
)