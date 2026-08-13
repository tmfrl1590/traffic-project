package com.system.traffic.presentation.screens.splash.state

import androidx.annotation.StringRes

data class SplashState(
    val isLoading: Boolean = false,
    @param:StringRes val messageRes: Int? = null,
    val progress: Float = 0f,
    val isComplete: Boolean = false,
    val isError: Boolean = false,
)
