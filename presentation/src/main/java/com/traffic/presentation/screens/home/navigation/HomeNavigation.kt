package com.traffic.presentation.screens.home.navigation

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.traffic.navigation.Screens
import com.traffic.presentation.screens.home.HomeScreenRoute

fun NavGraphBuilder.homeNavGraph(
    context: Context,
    onStationCardClick: (String, String) -> Unit,
    onGotoStation: () -> Unit,
) {
    composable<Screens.Home> {
        HomeScreenRoute(
            context = context,
            onStationCardClick = onStationCardClick,
            onGotoStation = onGotoStation,
        )
    }
}