package com.traffic.bus_arrive.viewmodel

import com.traffic.domain.model.StationModel

sealed class BusArriveUIEvents {
    data class OnFavoriteIconClick(val stationModel: StationModel) : BusArriveUIEvents()
}