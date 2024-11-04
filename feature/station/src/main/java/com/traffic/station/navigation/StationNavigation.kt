package com.traffic.station.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.silver.navigation.Screens
import com.traffic.station.StationScreen

fun NavGraphBuilder.stationNavGraph(
    navHostController: NavHostController,
    onStationCardClick: (String) -> Unit
) {
    composable<Screens.Station> {
        StationScreen(
            navHostController = navHostController,
            onStationCardClick = { stationId ->
                //navHostController.navigate(Screens.StationDetail.withArgs(stationId))
                //navHostController.navigate(Screens.BusArrive(arsId = stationId))
                onStationCardClick(stationId)
            }

        )
    }
}