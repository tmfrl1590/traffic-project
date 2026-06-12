package com.traffic.presentation.screens.bus_arrive.action

import com.traffic.domain.model.StationModel

sealed interface BusArriveAction {
    data class OnClickFavoriteIcon(val stationModel: StationModel) : BusArriveAction
    data object OnClickRefresh : BusArriveAction
}