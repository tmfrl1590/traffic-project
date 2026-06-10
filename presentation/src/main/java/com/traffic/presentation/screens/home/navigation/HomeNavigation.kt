package com.traffic.presentation.screens.home.navigation

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.silver.navigation.Screens
import com.traffic.presentation.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    context: Context,
    onStationCardClick: (String, String) -> Unit,
) {
    composable<Screens.Home> {
        HomeScreen(
            context = context,
            onStationCardClick = onStationCardClick
        )
    }
}