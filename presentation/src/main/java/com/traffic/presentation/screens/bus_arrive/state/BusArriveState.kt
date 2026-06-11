package com.traffic.presentation.screens.bus_arrive.state

import com.traffic.domain.model.StationModel
import com.traffic.presentation.screens.bus_arrive.model.BusArriveItemModel

data class BusArriveState(
    val isLoading: Boolean = false,
    val arriveList : List<BusArriveItemModel> = emptyList(),
    val stationInfo: StationModel = StationModel(
        stationNum = "",
        busStopName = "",
        nextBusStop = "",
        busStopId = "",
        arsId = "",
        longitude = "",
        latitude = "",
        selected = false
    )
)