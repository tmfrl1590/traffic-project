package com.traffic.domain.model

data class StationModel (
    val station_num : String,
    val busstop_name : String? = "",
    val next_busstop : String? = "",
    val busstop_id : String? = "",
    val ars_id : String? = "",
    val longitude: String? = "",
    val latitude: String? = "",
)