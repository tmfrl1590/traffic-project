package com.system.traffic.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.silver.navigation.Screens
import com.traffic.bus_arrive.BusArriveScreen
import com.traffic.bus_arrive.viewmodel.BusArriveUIEvents
import com.traffic.bus_arrive.viewmodel.BusArriveViewModel
import com.traffic.splash.navigation.splashGraph
import com.traffic.station.viewmodel.StationUIEvents
import com.traffic.station.viewmodel.StationViewModel

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
                onStationCardClick = { arsId, busStopId ->
                    navController.navigate(Screens.BusArrive(arsId = arsId, busStopId = busStopId))
                }
            )
        }

        composable<Screens.BusArrive> { backStackEntry ->
            val busArriveViewModel = hiltViewModel<BusArriveViewModel>()
            val arsId = backStackEntry.toRoute<Screens.BusArrive>().arsId
            val busStopId = backStackEntry.toRoute<Screens.BusArrive>().busStopId
            val state by busArriveViewModel.state.collectAsStateWithLifecycle()
            BusArriveScreen(
                context = context,
                state = state,
                arsId = arsId,
                busStopId = busStopId,
                snackBarHostState = snackBarHostState,
                busArriveViewModel = busArriveViewModel,
                onBackClick = { navController.popBackStack() },
                onFavoriteIconClick = { busArriveViewModel.onBusArriveUIEvents(BusArriveUIEvents.OnFavoriteIconClick(it)) }
            )
        }
    }
}