package com.traffic.presentation.model

import com.system.traffic.core.enum.LineType
import com.traffic.domain.model.LineStation
import com.traffic.domain.model.LineStationItem

data class LineStationModel(
    val resultCode: String,
    val resultMsg: String,
    val itemList: List<LineStationItemModel>,
    val rowCount: Int
)

data class LineStationItemModel(
    val busStopNum: Int,
    val lineId: Int,
    val lineName: String,
    val busStopId: Int,
    val busStopName: String,
    val arsId: Int?,
    val longitude: Double,
    val latitude: Double,
    val lineType: LineType,
    val seq: Int
)

fun LineStation.toPresentation() = LineStationModel(
    resultCode = resultCode,
    resultMsg = resultMsg,
    itemList = items.map { it.toPresentation() },
    rowCount = rowCount
)

fun LineStationItem.toPresentation() = LineStationItemModel(
    busStopNum = busStopNum,
    lineId = lineId,
    lineName = lineName,
    busStopId = busStopId,
    busStopName = busStopName,
    arsId = arsId,
    longitude = longitude,
    latitude = latitude,
    lineType = stationType,
    seq = seq
)
