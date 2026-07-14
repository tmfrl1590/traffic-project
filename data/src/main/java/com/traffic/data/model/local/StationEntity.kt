package com.traffic.data.model.local

import com.traffic.data.DataMapper
import com.traffic.domain.model.StationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StationEntity(
    @Serializable(with = AnyToStringSerializer::class)
    @SerialName("STATION_NUM") val stationNum : String,

    @SerialName("BUSSTOP_NAME") val busStopName : String?,
    @SerialName("NEXT_BUSSTOP") val nextBusStop : String?,

    @Serializable(with = NullableAnyToStringSerializer::class)
    @SerialName("BUSSTOP_ID") val busStopId : String?,
    @SerialName("ARS_ID") val arsId : String?,

    @Serializable(with = NullableAnyToStringSerializer::class)
    @SerialName("LONGITUDE") val longitude: String?,

    @Serializable(with = NullableAnyToStringSerializer::class)
    @SerialName("LATITUDE") val latitude: String?,
): DataMapper<StationModel>{
    override fun toDomain(): StationModel {
        return StationModel(
            stationNum = stationNum,
            busStopName = busStopName,
            nextBusStop = nextBusStop,
            busStopId = busStopId,
            arsId = arsId,
            longitude = longitude,
            latitude = latitude
        )
    }
}

fun StationModel.toEntity() = StationEntity(
    stationNum = stationNum,
    busStopName = busStopName,
    nextBusStop = nextBusStop,
    busStopId = busStopId,
    arsId = arsId,
    longitude = longitude,
    latitude = latitude
)