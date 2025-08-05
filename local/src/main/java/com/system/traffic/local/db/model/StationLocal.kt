package com.system.traffic.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.system.traffic.local.LocalConstants.STATION_LOCAL
import com.traffic.data.model.local.StationEntity

@Entity(tableName = STATION_LOCAL)
data class StationLocal (
    @PrimaryKey
    val stationNum : String,
    val busStopName : String?,
    val nextBusStop : String?,
    val busStopId : String?,
    val arsId : String?,
    val longitude: String?,
    val latitude: String?,
)

fun StationEntity.toStationEntity(): StationLocal {
    return StationLocal(
        stationNum = stationNum,
        busStopName = busStopName,
        nextBusStop = nextBusStop,
        busStopId = busStopId,
        arsId = arsId,
        longitude = longitude,
        latitude = latitude,
    )
}

fun StationLocal.toLikeStationModel(): StationEntity {
    return StationEntity(
        stationNum = stationNum,
        busStopName = busStopName ?: "",
        nextBusStop = nextBusStop ?: "",
        busStopId = busStopId ?: "",
        arsId = arsId ?: "",
        longitude = longitude ?: "",
        latitude = latitude ?: "",
    )
}