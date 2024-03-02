package com.system.traffic.domain.model

import com.google.gson.annotations.SerializedName

data class LineStationBody(
    @SerializedName("RESULT")
    val result : Map<String, String>,

    @SerializedName("BUSSTOP_LIST")
    val itemList : List<LineStationModel>,

    @SerializedName("ROW_COUNT")
    val row_count : String
)

data class LineStationModel (
    @SerializedName("LINE_ID")
    val line_id : String,

    @SerializedName("LINE_NAME")
    val line_name : String,

    @SerializedName("BUSSTOP_ID")
    val busstop_id : String,

    @SerializedName("BUSSTOP_NAME")
    val busstop_name : String,

    @SerializedName("LONGITUDE")
    val longitude : String,

    @SerializedName("LATITUDE")
    val latitude : String,

    @SerializedName("RETURN_FLAG")
    val return_flag : String,

    @SerializedName("ARS_ID")
    val ars_id : String,

    @SerializedName("BUSSTOP_NUM")
    val busstop_num : String

)