package com.system.traffic.main.navigation

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.silver.navigation.Screens
import com.system.traffic.main.ANIMATION_DURATION
import com.traffic.home.navigation.homeNavGraph
import com.traffic.line.navigation.lineNavGraph
import com.traffic.map.navigation.mapNavGraph
import com.traffic.setting.navigation.settingNavGraph
import com.traffic.station.navigation.stationNavGraph

@Composable
fun BottomBarGraph(
    context: Context,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
    onStationCardClick: (String, String) -> Unit,
) {
    NavHost(
        modifier = Modifier
            .background(Color.White)
            .padding(paddingValues),
        navController = navController,
        startDestination = Screens.Main,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
    ) {
        navigation<Screens.Main>(
            startDestination = Screens.Home
        ){

            homeNavGraph(
                context = context,
                onStationCardClick = onStationCardClick
            )
            stationNavGraph(
                snackBarHostState = snackBarHostState,
                onStationCardClick = onStationCardClick
            )
            lineNavGraph()

            mapNavGraph(
                context = context,
                snackBarHostState = snackBarHostState
            )
            settingNavGraph()
        }
    }
}