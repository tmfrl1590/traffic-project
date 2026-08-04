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

// nullable 필드는 반드시 기본값(= null)을 함께 지정한다.
// 기본값이 없으면 서버 응답에서 해당 키가 누락될 때 MissingFieldException으로 응답 전체가 실패한다.
@Serializable
data class BusArriveItemEntity(
    @SerialName("ARRIVE")
    val arrive: String? = null,

    @SerialName("REMAIN_STOP")
    val remainStop: Int? = null,

    @SerialName("SHORT_LINE_NAME")
    val shortLineName: String? = null,

    @SerialName("BUS_ID")
    val busId: String? = null,

    @SerialName("METRO_FLAG")
    val metroFlag: Int? = null,

    @SerialName("BUSSTOP_NAME")
    val busStopName: String? = null,

    @SerialName("CURR_STOP_ID")
    val currStopId: Int? = null,

    @SerialName("LINE_ID")
    val lineId: Int? = null,

    @SerialName("REMAIN_MIN")
    val remainMin: Int? = null,

    @SerialName("ENG_BUSSTOP_NAME")
    val engBusStopName: String? = null,

    @SerialName("DIR_START")
    val dirStart: String? = null,

    @SerialName("DIR_END")
    val dirEnd: String? = null,

    @SerialName("DIR")
    val dir: String? = null,

    @SerialName("LOW_BUS")
    val lowBus: String? = null,

    @SerialName("ARRIVE_FLAG")
    val arriveFlag: Int? = null,

    @SerialName("LINE_NAME")
    val lineName: String? = null,

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
