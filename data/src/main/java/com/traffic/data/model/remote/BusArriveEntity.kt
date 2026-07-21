package com.traffic.data.model.remote

import com.traffic.data.DataMapper
import com.traffic.domain.model.BusArrive
import com.traffic.domain.model.BusArriveItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusArriveEntity(
    @SerialName("RESPONSE")
    val response: BusArriveResponsePayload,
) : DataMapper<BusArrive> {
    override fun toDomain(): BusArrive {
        return BusArrive(
            itemList = response.arriveList?.items.orEmpty().map { it.toDomain() },
        )
    }
}

@Serializable
data class BusArriveResponsePayload(
    @SerialName("RESULT")
    val result: Map<String, String> = emptyMap(),
    @SerialName("ARRIVE_LIST")
    val arriveList: BusArriveItemListHolder? = null,
    @SerialName("ROW_COUNT")
    val rowCount: Int = 0,
)

@Serializable
data class BusArriveItemListHolder(
    @SerialName("ITEM")
    val items: List<BusArriveItemEntity> = emptyList(),
)

@Serializable
data class BusArriveItemEntity(
    @SerialName("ARRIVE")
    val arrive: String?,

    @SerialName("REMAIN_STOP")
    val remainStop: Int?,

    @SerialName("SHORT_LINE_NAME")
    val shortLineName: String?,

    @SerialName("BUS_ID")
    val busId: String?,

    @SerialName("METRO_FLAG")
    val metroFlag: Int?,

    @SerialName("BUSSTOP_NAME")
    val busStopName: String?,

    @SerialName("CURR_STOP_ID")
    val currStopId: Int?,

    @SerialName("LINE_ID")
    val lineId: Int?,

    @SerialName("REMAIN_MIN")
    val remainMin: Int?,

    @SerialName("ENG_BUSSTOP_NAME")
    val engBusStopName: String?,

    @SerialName("DIR_START")
    val dirStart: String?,

    @SerialName("DIR_END")
    val dirEnd: String?,

    @SerialName("DIR")
    val dir: String? = null,

    @SerialName("LOW_BUS")
    val lowBus: String?,

    @SerialName("ARRIVE_FLAG")
    val arriveFlag: Int?,

    @SerialName("LINE_NAME")
    val lineName: String?,

    @SerialName("LINE_KIND")
    val lineKind: Int? = null,
) : DataMapper<BusArriveItem> {
    override fun toDomain(): BusArriveItem {
        return BusArriveItem(
            arrive = arrive,
            remainStop = remainStop?.toString(),
            shortLineName = shortLineName,
            busId = busId,
            metroFlag = metroFlag?.toString(),
            busStopName = busStopName,
            currStopId = currStopId?.toString(),
            lineId = lineId?.toString(),
            remainMin = remainMin?.toString(),
            engBusStopName = engBusStopName,
            dirStart = dirStart,
            dir = dir,
            dirEnd = dirEnd,
            lowBus = lowBus,
            arriveFlag = arriveFlag?.toString(),
            lineName = lineName,
            lineKind = lineKind?.toString(),
            busLatitude = 0.0,
            busLongitude = 0.0,
            isPinned = false,
        )
    }
}
