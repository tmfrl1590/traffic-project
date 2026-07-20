package com.traffic.data.model.remote

import com.system.traffic.core.enum.LineType
import com.traffic.data.DataMapper
import com.traffic.domain.model.LineStation
import com.traffic.domain.model.LineStationItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineStationInfoEntity(
    @SerialName("RESPONSE")
    val response: LineStationInfoResponsePayload
): DataMapper<LineStation>{
    override fun toDomain(): LineStation {
        return LineStation(
            resultCode = response.result["RESULT_CODE"] ?: "",
            resultMsg = response.result["RESULT_MSG"] ?: "",
            items = response.lineStationInfoList?.items?.map { it.toDomain() } ?: emptyList(),
            rowCount = response.rowCount
        )
    }
}

@Serializable
data class LineStationInfoResponsePayload(
    @SerialName("RESULT")
    val result: Map<String, String> = emptyMap(),
    @SerialName("BUSSTOP_LIST")
    val lineStationInfoList: LineStationInfoListHolder? = null,
    @SerialName("ROW_COUNT")
    val rowCount: Int = 0,
)

@Serializable
data class LineStationInfoListHolder(
    @SerialName("ITEM")
    val items: List<LineStationItemEntity> = emptyList(),
)

@Serializable
data class LineStationItemEntity(
    @SerialName("BUSSTOP_NUM")
    val busStopNum: Int,
    @SerialName("LINE_ID")
    val lineId: Int,
    @SerialName("LINE_NAME")
    val lineName: String,
    @SerialName("BUSSTOP_ID")
    val busStopId: Int,
    @SerialName("BUSSTOP_NAME")
    val busStopName: String,
    @SerialName("ARS_ID")
    val arsId: Int? = null,
    @SerialName("LONGITUDE")
    val longitude: Double,
    @SerialName("LATITUDE")
    val latitude: Double,
    @SerialName("RETURN_FLAG")
    val returnFlag: Int,
    @SerialName("SEQ")
    val seq: Int
): DataMapper<LineStationItem>{
    override fun toDomain(): LineStationItem {
        return LineStationItem(
            busStopNum = busStopNum,
            lineId = lineId,
            lineName = lineName,
            busStopId = busStopId,
            busStopName = busStopName,
            arsId = arsId,
            longitude = longitude,
            latitude = latitude,
            stationType = LineType.fromFlag(returnFlag),
            seq = seq
        )
    }
}
