package com.system.traffic.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.system.traffic.local.LocalMapper
import com.system.traffic.local.RoomConstant
import com.traffic.data.model.local.StationEntity

@Entity(tableName = RoomConstant.Table.STATION_LOCAL)
data class StationLocal (
    @PrimaryKey
    val stationNum : String,
    val busStopName : String?,
    val nextBusStop : String?,
    val busStopId : String?,
    val arsId : String?,
    val longitude: String?,
    val latitude: String?,
): LocalMapper<StationEntity>{
    override fun toData(): StationEntity = StationEntity(
        stationNum = stationNum,
        busStopName = busStopName ?: "",
        nextBusStop = nextBusStop ?: "",
        busStopId = busStopId ?: "",
        arsId = arsId ?: "",
        longitude = longitude ?: "",
        latitude = latitude ?: "",
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