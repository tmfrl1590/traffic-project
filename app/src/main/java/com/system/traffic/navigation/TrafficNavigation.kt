package com.system.traffic.navigation

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.system.traffic.presentation.screen.home.HomeScreen
import com.system.traffic.presentation.screen.line.LineScreen
import com.system.traffic.presentation.screen.setting.SettingScreen
import com.system.traffic.presentation.screen.splash.SplashScreen
import com.system.traffic.presentation.screen.station.StationScreen

const val ANIMATION_DURATION = 500

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value.fromRoute()

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
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
                SplashScreen(navHostController = navController)
            }
            composable<Screens.Home> {
                HomeScreen(navHostController = navController)
            }
            composable<Screens.Station> {
                StationScreen(navHostController = navController)
            }
            composable<Screens.Line> {
                LineScreen()
            }
            composable<Screens.Setting> {
                SettingScreen()
            }
        }
    }
}