package com.traffic.presentation.screens.main.navigation

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.traffic.navigation.BottomBarScreen
import com.traffic.navigation.Screens
import com.traffic.presentation.screens.home.navigation.homeNavGraph
import com.traffic.presentation.screens.main.ANIMATION_DURATION
import com.traffic.presentation.screens.setting.navigation.settingNavGraph
import com.traffic.presentation.screens.station.navigation.stationNavGraph


@Composable
fun BottomBarGraph(
    context: Context,
    navController: NavHostController,
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
                onStationCardClick = onStationCardClick,
                onGotoStation = {
                    navController.navigate(Screens.Station){
                        popUpTo(navController.graph.findStartDestination().route!!){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            stationNavGraph(
                onStationCardClick = onStationCardClick
            )
            settingNavGraph()
        }
    }
}