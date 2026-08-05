package com.system.traffic.presentation.screens.bus_arrive.action

import com.system.traffic.domain.model.StationModel

sealed interface BusArriveAction {
    data class OnClickFavoriteIcon(val stationModel: StationModel) : BusArriveAction
    data object OnClickRefresh : BusArriveAction
    data class OnClickPinnedIcon(val lineId: String, val isPinned: Boolean) : BusArriveAction
}