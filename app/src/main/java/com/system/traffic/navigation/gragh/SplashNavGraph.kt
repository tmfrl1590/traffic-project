package com.system.traffic.navigation.gragh

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.system.traffic.navigation.Graph
import com.system.traffic.navigation.SplashNav
import com.system.traffic.presentation.screen.splash.SplashScreen

fun NavGraphBuilder.splashNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.SPLASH,
        startDestination = SplashNav.SPLASH.route
    ) {
        composable(route = SplashNav.SPLASH.route) {
            SplashScreen(navHostController = navController)
        }
    }
}