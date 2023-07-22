package com.system.traffic.db.entity

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
    var selected : String?
)