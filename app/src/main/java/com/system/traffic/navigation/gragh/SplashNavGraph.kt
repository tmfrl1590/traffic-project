package com.system.traffic.navigation.gragh

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.system.traffic.navigation.BusArriveNav
import com.system.traffic.navigation.Graph
import com.system.traffic.navigation.MainNav
import com.system.traffic.navigation.SplashNav
import com.system.traffic.presentation.screen.bus_arrive.BusArriveScreen
import com.system.traffic.presentation.screen.home.HomeScreen
import com.system.traffic.presentation.screen.line.LineScreen
import com.system.traffic.presentation.screen.splash.SplashScreen
import com.system.traffic.presentation.screen.station.StationScreen

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

@Composable
fun HomeGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.HOME,
        startDestination = MainNav.HOME.route,
    ) {
        composable(route = MainNav.HOME.route) {
            HomeScreen(navController)
        }
        composable(route = MainNav.STATION.route) {
            StationScreen(navController)
        }
        composable(route = MainNav.LINE.route) {
            LineScreen()
        }

        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = "${Graph.BUS_ARRIVE}/{id}",
        startDestination = "${BusArriveNav.route}/{id}",
    ) {
        composable(
            route = "${BusArriveNav.route}/{id}",
            arguments = BusArriveNav.arguments,
            deepLinks = BusArriveNav.deepLinks,
        ) { entry ->
            val arsId = entry.arguments?.getString("id")
            if (arsId != null) {
                BusArriveScreen(
                    arsId = arsId,
                    navController = navController
                )
            }
        }
    }
}