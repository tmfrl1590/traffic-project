package com.traffic.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.silver.navigation.Screens
import com.traffic.splash.SplashScreen

fun NavGraphBuilder.splashGraph(
    navHostController: NavHostController,
){
    composable<Screens.Splash> {
        SplashScreen(
            onGoHomeScreen = {
                navHostController.popBackStack()
                navHostController.navigate(Screens.Main)
            }
        )
    }
}