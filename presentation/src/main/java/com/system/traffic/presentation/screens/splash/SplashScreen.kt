package com.system.traffic.presentation.screens.splash

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
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.system.traffic.design.R
import com.system.traffic.presentation.firebase.ScreenName
import com.system.traffic.presentation.firebase.TrackScreenView
import com.system.traffic.presentation.screens.splash.state.SplashState
import com.system.traffic.presentation.screens.splash.viewmodel.SplashViewModel

@Composable
fun SplashScreenRoute(
    onGoHomeScreen: () -> Unit = {},
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    TrackScreenView(screenName = ScreenName.Splash)

    val scale = remember { Animatable(0f) }
    val state by splashViewModel.state.collectAsStateWithLifecycle()
    var animationDone by remember { mutableStateOf(false) }

    // 애니메이션은 초기화와 분리해 병렬로 진행
    LaunchedEffect(key1 = Unit) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = { OvershootInterpolator(8f).getInterpolation(it) }
            )
        )
        animationDone = true
    }

    // 콜백 대신 상태 관찰: 초기화 완료 + 애니메이션 완료 시 Home으로 이동.
    // 항상 "현재" ViewModel의 상태를 보므로 ViewModel이 재생성되어도 신호가 유실되지 않는다.
    LaunchedEffect(key1 = state.isComplete, key2 = animationDone) {
        if (state.isComplete && animationDone) {
            onGoHomeScreen()
        }
    }

    SplashScreen(
        scale = scale,
        state = state,
        onRetry = splashViewModel::retry
    )
}

@Composable
private fun SplashScreen(
    scale: Animatable<Float, AnimationVector1D>,
    state: SplashState,
    onRetry: () -> Unit,
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
                    .scale(scale.value)
                    .testTag(SplashTestTags.LOGO)
                ,
                painter = painterResource(id = R.drawable.main_bus),
                contentDescription = "main_logo"
            )

            if (state.isError) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.splash_load_error),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onRetry) {
                    Text(text = stringResource(R.string.retry))
                }
            } else if (state.isLoading) {
                Spacer(modifier = Modifier.height(32.dp))

                LinearProgressIndicator(
                    progress = { state.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp),
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.messageRes?.let { stringResource(it) }.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}