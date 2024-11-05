package com.traffic.station.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.silver.navigation.Screens
import com.traffic.station.StationScreen
import com.traffic.station.viewmodel.StationUIEvents
import com.traffic.station.viewmodel.StationViewModel

fun NavGraphBuilder.stationNavGraph(
    onStationCardClick: (String) -> Unit
) {
    composable<Screens.Station> {
        val stationViewModel = hiltViewModel<StationViewModel>()
        StationScreen(
            onStationCardClick = { onStationCardClick(it) },
            onSearchStation = { stationViewModel.onStationUIEvents(StationUIEvents.OnSearchStationList(it)) },
            onFavoriteIconClick = { stationViewModel.onStationUIEvents(StationUIEvents.OnFavoriteIconClick(it)) }
        )
    }
}

