package com.traffic.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.traffic.common.R
import com.traffic.splash.component.saveStationAndLineJsonData
import com.traffic.splash.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    onGoHomeScreen: () -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        //logEvent(context, "SplashScreen")

        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = { OvershootInterpolator(8f).getInterpolation(it) }
            )
        )
        val isCheckFirstLogin = splashViewModel.getIsFirstLogin()
        if (isCheckFirstLogin) {
            splashViewModel.setUpIsFirstLogin()
            saveStationAndLineJsonData(splashViewModel)
        }
        onGoHomeScreen()
    }

    SplashScreenContent(
        scale = scale
    )
}

@Composable
private fun SplashScreenContent(
    scale: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier
                .size(300.dp)
                .scale(scale.value),
            painter = painterResource(id = R.drawable.main_bus),
            contentDescription = "main_logo"
        )
    }
}