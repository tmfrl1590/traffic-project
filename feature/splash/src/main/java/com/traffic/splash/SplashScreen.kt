package com.traffic.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
    val loadingState by splashViewModel.loadingState.collectAsState()

    LaunchedEffect(key1 = true) {
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
        scale = scale,
        loadingState = loadingState
    )
}

@Composable
private fun SplashScreenContent(
    scale: Animatable<Float, AnimationVector1D>,
    loadingState: com.traffic.splash.viewmodel.DataLoadingState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(300.dp)
                    .scale(scale.value),
                painter = painterResource(id = R.drawable.main_bus),
                contentDescription = "main_logo"
            )

            if (loadingState.isLoading) {
                Spacer(modifier = Modifier.height(32.dp))

                LinearProgressIndicator(
                    progress = { loadingState.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp),
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = loadingState.message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}