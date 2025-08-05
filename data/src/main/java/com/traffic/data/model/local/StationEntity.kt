package com.traffic.data.model.local

import com.traffic.data.DataMapper
import com.traffic.domain.model.StationModel

data class StationEntity(
    val stationNum : String,
    val busStopName : String?,
    val nextBusStop : String?,
    val busStopId : String?,
    val arsId : String?,
    val longitude: String?,
    val latitude: String?,
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

/*
fun StationEntity.toModel() = StationModel(
    stationNum = stationNum,
    busStopName = busStopName,
    nextBusStop = nextBusStop,
    busStopId = busStopId,
    arsId = arsId,
    longitude = longitude,
    latitude = latitude
)*/
