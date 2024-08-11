package com.traffic.domain.model

import kotlinx.serialization.SerialName

data class LineStationBody(
    @SerialName("RESULT")
    val result : Map<String, String>,

    @SerialName("BUSSTOP_LIST")
    val itemList : List<LineStationModel>,

    @SerialName("ROW_COUNT")
    val rowCount : String
)

data class LineStationModel (
    @SerialName("LINE_ID")
    val lineId : String,

    @SerialName("LINE_NAME")
    val lineName : String,

    @SerialName("BUSSTOP_ID")
    val busStopId : String,

    @SerialName("BUSSTOP_NAME")
    val busStopName : String,

    @SerialName("LONGITUDE")
    val longitude : String,

    @SerialName("LATITUDE")
    val latitude : String,

    @SerialName("RETURN_FLAG")
    val returnFlag : String,

    @SerialName("ARS_ID")
    val arsId : String,

    @SerialName("BUSSTOP_NUM")
    val busStopNum : String

)