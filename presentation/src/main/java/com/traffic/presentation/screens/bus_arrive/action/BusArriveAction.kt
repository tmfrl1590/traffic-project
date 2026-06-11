package com.traffic.presentation.screens.bus_arrive.action

import com.traffic.domain.model.StationModel

sealed interface BusArriveAction {
    data class OnFavoriteIconClick(val stationModel: StationModel) : BusArriveAction
}