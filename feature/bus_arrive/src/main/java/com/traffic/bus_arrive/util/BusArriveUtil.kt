package com.traffic.bus_arrive.util

import com.traffic.domain.model.StationModel
import com.traffic.station.viewmodel.StationViewModel

fun busArriveScreenTitleText(
    stationInfo: StationModel,
): String{
    return "${stationInfo.busStopName} (${stationInfo.arsId})"
}

fun insertOrDeleteStationInfo(
    stationModel: StationModel,
    stationViewModel: StationViewModel,
){
    if(stationModel.selected){
        stationViewModel.deleteLikeStation(stationModel.busStopId ?: "")
    }else {
        stationViewModel.insertLikeStation(stationModel)
    }
}