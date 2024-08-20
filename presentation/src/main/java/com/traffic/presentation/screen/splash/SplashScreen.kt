package com.traffic.presentation.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.silver.navigation.Screens

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                })
        )
        val isCheckFirstLogin = splashViewModel.getIsFirstLogin()

        if (isCheckFirstLogin) {
            splashViewModel.setUpIsFirstLogin()

            setFileData(splashViewModel)
        }

        navHostController.popBackStack()
        navHostController.navigate(Screens.Home)
    }

    SplashBackground(
        scale = scale
    )
}