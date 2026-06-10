package com.traffic.presentation.screens.bus_arrive.util

import com.traffic.domain.model.StationModel
import com.traffic.presentation.screens.station.viewmodel.StationViewModel

fun busArriveScreenTitleText(
    stationInfo: StationModel,
): String{
    return "${stationInfo.busStopName} (${stationInfo.arsId})"
}