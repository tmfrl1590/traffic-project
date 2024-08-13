package com.traffic.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.traffic.common.Constants.STATION_ENTITY
import com.traffic.domain.model.StationModel

@Entity(tableName = STATION_ENTITY)
data class StationEntity (
    @PrimaryKey
    val stationNum : String,
    val busStopName : String?,
    val nextBusStop : String?,
    val busStopId : String?,
    val arsId : String?,
    val longitude: String?,
    val latitude: String?,
)

fun StationModel.toStationEntity(): StationEntity {
    return StationEntity(
        stationNum = stationNum,
        busStopName = busStopName,
        nextBusStop = nextBusStop,
        busStopId = busStopId,
        arsId = arsId,
        longitude = longitude,
        latitude = latitude,
    )
}

fun StationEntity.toLikeStationModel(): StationModel {
    return StationModel(
        stationNum = stationNum,
        busStopName = busStopName ?: "",
        nextBusStop = nextBusStop ?: "",
        busStopId = busStopId ?: "",
        arsId = arsId ?: "",
        longitude = longitude ?: "",
        latitude = latitude ?: "",
    )
}