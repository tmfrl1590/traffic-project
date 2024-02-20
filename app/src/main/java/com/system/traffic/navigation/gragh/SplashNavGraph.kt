package com.system.traffic.navigation.gragh

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.system.traffic.navigation.BusArriveNav
import com.system.traffic.navigation.Graph
import com.system.traffic.navigation.MainNav
import com.system.traffic.navigation.SplashNav
import com.system.traffic.presentation.screen.bus_arrive.BusArriveScreen
import com.system.traffic.presentation.screen.home.LikeScreen
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
fun HomeGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = MainNav.LIKE.route
    ) {
        composable(route = MainNav.LIKE.route) {
            LikeScreen(navController)
        }
        composable(route = MainNav.STATION.route) {
            StationScreen(navController)
        }
        composable(route = MainNav.LINE.route) {
            LineScreen(navController)
        }

        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = "${Graph.BUS_ARRIVE}/{id}",
        startDestination = BusArriveNav.route,

    ) {
        println("test111 $id")
        composable(
            route = BusArriveNav.route,
            arguments = BusArriveNav.arguments,
            /*arguments = listOf(navArgument("id"){
                type = NavType.IntType
            }),*/
            deepLinks = BusArriveNav.deepLinks,
        ) {
            val busArriveString = BusArriveNav.findArgument(it)
            if (busArriveString != null) {
                BusArriveScreen(busArriveString)
            }else{
                BusArriveScreen("1234")
            }
        }
    }
}