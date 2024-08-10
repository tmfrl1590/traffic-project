package com.traffic.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.traffic.common.Constants.STATION_ENTITY
import com.traffic.domain.model.StationModel

@Entity(tableName = STATION_ENTITY)
data class StationEntity (
    @PrimaryKey
    val station_num : String,
    val busstop_name : String?,
    val next_busstop : String?,
    val busstop_id : String?,
    val ars_id : String?,
    val longitude: String?,
    val latitude: String?,
)

fun StationModel.toStationEntity(): StationEntity {
    return StationEntity(
        station_num = station_num,
        busstop_name = busstop_name,
        next_busstop = next_busstop,
        busstop_id = busstop_id,
        ars_id = ars_id,
        longitude = longitude,
        latitude = latitude,
    )
}

fun StationEntity.toLikeStationModel(): StationModel {
    return StationModel(
        station_num = station_num,
        busstop_name = busstop_name ?: "",
        next_busstop = next_busstop ?: "",
        busstop_id = busstop_id ?: "",
        ars_id = ars_id ?: "",
        longitude = longitude ?: "",
        latitude = latitude ?: "",
    )
}