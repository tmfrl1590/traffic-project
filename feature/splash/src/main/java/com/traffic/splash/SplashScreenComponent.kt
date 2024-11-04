package com.traffic.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.traffic.common.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

@Composable
fun SplashBackground(
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
            contentDescription = ""
        )
    }
}

suspend fun setFileData(
    splashViewModel: SplashViewModel,
){
    val insertStationJob = CoroutineScope(Dispatchers.IO).launch {
        val stationList = splashViewModel.getFileStationData()
        for(item in stationList){
            splashViewModel.insertStationInfo(item)
        }
    }

    val insertLineJob = CoroutineScope(Dispatchers.IO).launch {
        val lineList = splashViewModel.getFileLineData()
        for(item in lineList){
            splashViewModel.insertLineInfo(item)
        }
    }

    joinAll(insertStationJob, insertLineJob)
}