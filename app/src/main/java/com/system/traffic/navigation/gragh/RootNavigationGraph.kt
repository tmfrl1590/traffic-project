package com.system.traffic.navigation.gragh

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.system.traffic.navigation.Graph
import com.system.traffic.navigation.screen.MainScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ) {
        splashNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            MainScreen()
        }

    }
}