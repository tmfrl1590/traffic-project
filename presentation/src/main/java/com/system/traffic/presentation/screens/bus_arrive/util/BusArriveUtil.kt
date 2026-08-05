package com.system.traffic.presentation.screens.bus_arrive.util

import com.system.traffic.domain.model.StationModel
import com.system.traffic.presentation.screens.station.viewmodel.StationViewModel

fun busArriveScreenTitleText(
    stationInfo: StationModel,
): String{
    return "${stationInfo.busStopName} (${stationInfo.arsId})"
}