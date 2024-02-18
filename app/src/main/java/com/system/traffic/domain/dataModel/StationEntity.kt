package com.system.traffic.domain.dataModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "station")
data class StationEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val station_num : String?,
    val busstop_name : String?,
    val next_busstop : String?,
    val busstop_id : String?,
    val ars_id : String?,
    val longitude: String?,
    val latitude: String?,
    var selected : Boolean
)

fun StationModel.toStationEntity(): StationEntity {
    return StationEntity(
        id = 0,
        station_num = station_num,
        busstop_name = busstop_name,
        next_busstop = next_busstop,
        busstop_id = busstop_id,
        ars_id = ars_id,
        longitude = longitude,
        latitude = latitude,
        selected = false,
    )
}