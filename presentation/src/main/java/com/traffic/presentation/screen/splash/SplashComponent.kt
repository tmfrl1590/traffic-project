package com.traffic.presentation.screen.splash

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
import com.traffic.presentation.R

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