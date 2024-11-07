package com.traffic.home.navigation

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.silver.navigation.Screens
import com.traffic.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    context: Context,
    navHostController: NavHostController,
) {
    composable<Screens.Home> {
        HomeScreen(navHostController = navHostController, context = context)
    }
}