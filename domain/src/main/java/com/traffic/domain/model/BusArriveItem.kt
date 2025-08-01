package com.traffic.domain.model

data class BusArrive(
    val itemList: List<BusArriveItem>
)

data class BusArriveItem(
    val arrive : String?,
    val remainStop : String?,
    val shortLineName : String?,
    val busId : String?,
    val metroFlag : String?,
    val busStopName : String?,
    val currStopId : String?,
    val lineId : String?,
    val remainMin : String?,
    val engBusStopName : String?,
    val dirStart : String?,
    val dir : String?,
    val dirEnd : String?,
    val lowBus : String?,
    val arriveFlag : String?,
    val lineName : String?,
    var lineKind : String? = null,
)