package com.system.traffic.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.system.traffic.local.RoomConstant
import com.traffic.data.model.local.StationEntity

@Entity(tableName = RoomConstant.Table.STATION_LIKE_LOCAL)
data class LikeStationLocal(
    @PrimaryKey
    val station_num : String,
    val busstop_name : String?,
    val next_busstop : String?,
    val busstop_id : String?,
    val ars_id : String?,
    val longitude: String?,
    val latitude: String?,
)

fun StationEntity.toLikeStationEntity(): LikeStationLocal {
    return LikeStationLocal(
        station_num = stationNum,
        busstop_name = busStopName,
        next_busstop = nextBusStop,
        busstop_id = busStopId,
        ars_id = arsId,
        longitude = longitude,
        latitude = latitude,
    )
}

fun LikeStationLocal.toLikeStationModel(): StationEntity {
    return StationEntity(
        stationNum = station_num,
        busStopName = busstop_name!!,
        nextBusStop = next_busstop!!,
        busStopId = busstop_id!!,
        arsId = ars_id!!,
        longitude = longitude!!,
        latitude = latitude!!,
    )
}