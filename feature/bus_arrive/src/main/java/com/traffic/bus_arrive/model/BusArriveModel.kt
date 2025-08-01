package com.traffic.bus_arrive.model

import com.traffic.domain.model.BusArrive
import com.traffic.domain.model.BusArriveItem

data class BusArriveModel(
    val itemList: List<BusArriveItemModel>
)

data class BusArriveItemModel(
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

fun BusArrive.toPresentation(): BusArriveModel = BusArriveModel(
    itemList = itemList.map { it.toPresentation() }
)

fun BusArriveItem.toPresentation(): BusArriveItemModel = BusArriveItemModel(
    arrive = arrive,
    remainStop = remainStop,
    shortLineName = shortLineName,
    busId = busId,
    metroFlag = metroFlag,
    busStopName = busStopName,
    currStopId = currStopId,
    lineId = lineId,
    remainMin = remainMin,
    engBusStopName = engBusStopName,
    dirStart = dirStart,
    dir = dir,
    dirEnd = dirEnd,
    lowBus = lowBus,
    arriveFlag = arriveFlag,
    lineName = lineName
)