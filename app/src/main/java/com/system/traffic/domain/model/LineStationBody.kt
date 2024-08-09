package com.system.traffic.domain.model

import kotlinx.serialization.SerialName


data class LineStationBody(
    //@SerializedName("RESULT")
    @SerialName("RESULT")
    val result : Map<String, String>,

    //@SerializedName("BUSSTOP_LIST")
    @SerialName("BUSSTOP_LIST")
    val itemList : List<LineStationModel>,

    //@SerializedName("ROW_COUNT")
    @SerialName("ROW_COUNT")
    val row_count : String
)

data class LineStationModel (

    //@SerializedName("LINE_ID")
    @SerialName("LINE_ID")
    val line_id : String,

    //@SerializedName("LINE_NAME")
    @SerialName("LINE_NAME")
    val line_name : String,

    //@SerializedName("BUSSTOP_ID")
    @SerialName("BUSSTOP_ID")
    val busstop_id : String,

    //@SerializedName("BUSSTOP_NAME")
    @SerialName("BUSSTOP_NAME")
    val busstop_name : String,

    //@SerializedName("LONGITUDE")
    @SerialName("LONGITUDE")
    val longitude : String,

    //@SerializedName("LATITUDE")
    @SerialName("LATITUDE")
    val latitude : String,

    //@SerializedName("RETURN_FLAG")
    @SerialName("RETURN_FLAG")
    val return_flag : String,

    //@SerializedName("ARS_ID")
    @SerialName("ARS_ID")
    val ars_id : String,

    //@SerializedName("BUSSTOP_NUM")
    @SerialName("BUSSTOP_NUM")
    val busstop_num : String

)