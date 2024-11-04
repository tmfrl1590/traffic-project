package com.system.traffic.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.silver.navigation.BottomNavigationBar
import com.silver.navigation.Screens
import com.silver.navigation.fromRoute
import com.traffic.bus_arrive.BusArriveScreen
import com.traffic.home.HomeScreen
import com.traffic.line.LineScreen
import com.traffic.setting.SettingScreen
import com.traffic.splash.SplashScreen
import com.traffic.station.StationScreen

const val ANIMATION_DURATION = 500

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value.fromRoute()

    val snackBarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                context = context,
            )
        }
    ){
        NavHost(
            navController = navController,
            startDestination = Screens.Splash,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
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
            composable<Screens.Splash> {
                SplashScreen(
                    context = context,
                    navHostController = navController
                )
            }
            composable<Screens.Home> {
                HomeScreen(navHostController = navController, context = context)
            }
            composable<Screens.Station> {
                StationScreen(
                    context = context,
                    navHostController = navController
                )
            }
            composable<Screens.Line> {
                LineScreen(context = context)
            }
            composable<Screens.Map> {
                com.traffic.map.MapScreen(context = context, snackBarHostState = snackBarHostState)
            }
            composable<Screens.Setting> {
                SettingScreen(context = context)
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
}