package com.traffic.domain.model

import com.system.traffic.core.enum.LineType

data class LineStation(
    val resultCode: String,
    val resultMsg: String,
    val items: List<LineStationItem>,
    val rowCount: Int
)

data class LineStationItem(
    val busStopNum: Int,
    val lineId: Int,
    val lineName: String,
    val busStopId: Int,
    val busStopName: String,
    val arsId: Int?,
    val longitude: Double,
    val latitude: Double,
    val stationType: LineType,
    val seq: Int
)
