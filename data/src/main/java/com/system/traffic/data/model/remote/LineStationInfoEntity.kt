package com.system.traffic.data.model.remote

import com.system.traffic.core.enums.LineType
import com.system.traffic.data.DataMapper
import com.system.traffic.domain.model.LineStation
import com.system.traffic.domain.model.LineStationItem
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

// 모든 필드에 기본값 지정 — 키 누락 시 MissingFieldException으로 응답 전체가 실패하는 것 방지
// (coerceInputValues=true 덕분에 명시적 null도 기본값으로 대체됨)
@Serializable
data class LineStationItemEntity(
    @SerialName("BUSSTOP_NUM")
    val busStopNum: Int = 0,
    @SerialName("LINE_ID")
    val lineId: Int = 0,
    @SerialName("LINE_NAME")
    val lineName: String = "",
    @SerialName("BUSSTOP_ID")
    val busStopId: Int = 0,
    @SerialName("BUSSTOP_NAME")
    val busStopName: String = "",
    @SerialName("ARS_ID")
    val arsId: Int? = null,
    @SerialName("LONGITUDE")
    val longitude: Double = 0.0,
    @SerialName("LATITUDE")
    val latitude: Double = 0.0,
    @SerialName("RETURN_FLAG")
    val returnFlag: Int = 0,
    @SerialName("SEQ")
    val seq: Int = 0
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
