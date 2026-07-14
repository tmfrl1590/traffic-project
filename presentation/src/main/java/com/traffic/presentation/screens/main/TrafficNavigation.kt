package com.traffic.presentation.screens.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.traffic.navigation.Screens
import com.traffic.presentation.screens.bus_arrive.BusArriveScreenRoute
import com.traffic.presentation.screens.splash.navigation.splashGraph


const val ANIMATION_DURATION = 200

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screens.Splash,
        modifier = Modifier
            .fillMaxSize(),
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = ANIMATION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = ANIMATION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = ANIMATION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = ANIMATION_DURATION)
            )
        }
    ){
        splashGraph(
            navHostController = navController
        )

        composable<Screens.Main> {
            MainScreen(
                snackBarHostState = snackBarHostState,
                onStationCardClick = { arsId, busStopId ->
                    navController.navigate(Screens.BusArrive(arsId = arsId, busStopId = busStopId))
                }
            )
        }

        composable<Screens.BusArrive> { backStackEntry ->
            val arsId = backStackEntry.toRoute<Screens.BusArrive>().arsId
            val busStopId = backStackEntry.toRoute<Screens.BusArrive>().busStopId
            BusArriveScreenRoute(
                arsId = arsId,
                busStopId = busStopId,
                snackBarHostState = snackBarHostState,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}