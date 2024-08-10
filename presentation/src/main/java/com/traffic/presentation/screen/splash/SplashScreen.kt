package com.traffic.presentation.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.traffic.presentation.navigation.Screens
import com.traffic.presentation.screen.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel(),
    commonViewModel: CommonViewModel = hiltViewModel(),
){
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

        val isFirstLogin = commonViewModel.checkIsFirstLogin()
        if(!isFirstLogin){
            commonViewModel.setUpIsFirstLogin()

            launch(Dispatchers.IO) {
                val stationList = splashViewModel.getFileStationData()
                for(item in stationList){
                    splashViewModel.insertStationInfo(item)
                }
            }

            launch(Dispatchers.IO){
                val lineList = splashViewModel.getFileLineData()
                for(item in lineList){
                    splashViewModel.insertLineInfo(item)
                }
            }
        }

        delay(1000L)

        navHostController.popBackStack()
        navHostController.navigate(Screens.Home)
        //navHostController.navigate(Graph.HOME)

    }

    SplashBackground(
        scale = scale
    )
}