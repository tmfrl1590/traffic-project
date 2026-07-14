package com.traffic.presentation.screens.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.traffic.navigation.Screens
import com.traffic.presentation.screens.splash.SplashScreenRoute

fun NavGraphBuilder.splashGraph(
    navHostController: NavHostController,
){
    composable<Screens.Splash> {
        SplashScreenRoute(
            onGoHomeScreen = {
                navHostController.popBackStack()
                navHostController.navigate(Screens.Main)
            }
        )
    }
}