package com.system.traffic.main

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
import com.silver.navigation.Screens
import com.traffic.bus_arrive.BusArriveScreen
import com.traffic.splash.navigation.splashGraph

const val ANIMATION_DURATION = 500

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
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
    ){
        splashGraph(
            navHostController = navController
        )

        composable<Screens.Main> {
            MainScreen(
                snackBarHostState = snackBarHostState,
                onStationCardClick = { stationId ->
                    navController.navigate(Screens.BusArrive(arsId = stationId))
                }
            )
        }

        composable<Screens.BusArrive> { backStackEntry ->
            val arsId = backStackEntry.toRoute<Screens.BusArrive>().arsId
            BusArriveScreen(
                context = context,
                arsId = arsId,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
    }
}