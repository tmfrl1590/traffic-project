package com.traffic.presentation.screens.station.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.silver.navigation.Screens
import com.traffic.presentation.screens.station.viewmodel.StationUIEvents

fun NavGraphBuilder.stationNavGraph(
    snackBarHostState: SnackbarHostState,
    onStationCardClick: (String, String) -> Unit
) {
    composable<Screens.Station> {
        val stationViewModel = hiltViewModel<com.traffic.presentation.screens.station.viewmodel.StationViewModel>()
        _root_ide_package_.com.traffic.presentation.screens.station.StationScreen(
            snackBarHostState = snackBarHostState,
            onStationCardClick = onStationCardClick,
            onSearchStation = {
                stationViewModel.onStationUIEvents(
                    StationUIEvents.OnSearchStationList(
                        it
                    )
                )
            },
            onFavoriteIconClick = {
                stationViewModel.onStationUIEvents(
                    StationUIEvents.OnFavoriteIconClick(
                        it
                    )
                )
            }
        )
    }
}

