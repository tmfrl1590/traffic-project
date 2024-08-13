package com.traffic.domain.model

data class StationModel (
    val stationNum : String,
    val busStopName : String?,
    val nextBusStop : String?,
    val busStopId : String?,
    val arsId : String?,
    val longitude: String?,
    val latitude: String?,
    var selected: Boolean = false,
)