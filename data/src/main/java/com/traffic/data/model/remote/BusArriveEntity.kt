package com.traffic.data.model.remote

import com.traffic.data.DataMapper
import com.traffic.domain.model.BusArrive
import com.traffic.domain.model.BusArriveItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusArriveEntity(
    @SerialName("RESULT")
    val result : Map<String, String>,
    @SerialName("BUSSTOP_LIST")
    val itemList : List<BusArriveItemEntity>,
    @SerialName("ROW_COUNT")
    val rowCount : String
): DataMapper<BusArrive>{
    override fun toDomain(): BusArrive {
        return BusArrive(
            itemList = itemList.map { it.toDomain() }
        )
    }
}

@Serializable
data class BusArriveItemEntity(
    @SerialName("ARRIVE")
    val arrive : String?,

    @SerialName("REMAIN_STOP")
    val remainStop : String?,

    @SerialName("SHORT_LINE_NAME")
    val shortLineName : String?,

    @SerialName("BUS_ID")
    val busId : String?,

    @SerialName("METRO_FLAG")
    val metroFlag : String?,

    @SerialName("BUSSTOP_NAME")
    val busStopName : String?,

    @SerialName("CURR_STOP_ID")
    val currStopId : String?,

    @SerialName("LINE_ID")
    val lineId : String?,

    @SerialName("REMAIN_MIN")
    val remainMin : String?,

    @SerialName("ENG_BUSSTOP_NAME")
    val engBusStopName : String?,

    @SerialName("DIR_START")
    val dirStart : String?,

    @SerialName("DIR")
    val dir : String?,

    @SerialName("DIR_END")
    val dirEnd : String?,

    @SerialName("LOW_BUS")
    val lowBus : String?,

    @SerialName("ARRIVE_FLAG")
    val arriveFlag : String?,

    @SerialName("LINE_NAME")
    val lineName : String?,

    var lineKind : String? = null,
): DataMapper<BusArriveItem>{
    override fun toDomain(): BusArriveItem {
        return BusArriveItem(
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
            lineName = lineName,
            lineKind = lineKind,
        )
    }
}