package com.traffic.presentation.screens.station.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.traffic.navigation.Screens
import com.traffic.presentation.screens.station.StationScreenRoute

fun NavGraphBuilder.stationNavGraph(
    onStationCardClick: (String, String) -> Unit
) {
    composable<Screens.Station> {
        StationScreenRoute(
            onStationCardClick = onStationCardClick,
        )
    }
}

