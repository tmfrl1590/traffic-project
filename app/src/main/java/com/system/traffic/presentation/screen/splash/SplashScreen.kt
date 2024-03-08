package com.system.traffic.presentation.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.system.traffic.R
import com.system.traffic.data.local.db.entity.toLineEntity
import com.system.traffic.data.local.db.entity.toStationEntity
import com.system.traffic.navigation.Graph
import com.system.traffic.presentation.screen.CommonViewModel
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


        val a = commonViewModel.checkIsFirstLogin()
        if(a){
            navHostController.popBackStack()
            navHostController.navigate(Graph.HOME)
        }else{
            commonViewModel.setUpIsFirstLogin()

            launch(Dispatchers.IO) {
                val stationList = splashViewModel.getFileStationData()
                for(item in stationList){
                    splashViewModel.insertStationInfo(item.toStationEntity())
                }
            }

            launch(Dispatchers.IO){
                val lineList = splashViewModel.getFileLineData()
                for(item in lineList){
                    splashViewModel.insertLineInfo(item.toLineEntity())
                }
            }
        }

        delay(1500L)

        navHostController.popBackStack()
        navHostController.navigate(Graph.HOME)
    }

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
            contentDescription = ""
        )
    }


    val stationScope = rememberCoroutineScope()
    val lineScope = rememberCoroutineScope()

    LaunchedEffect(Unit){

    }
}