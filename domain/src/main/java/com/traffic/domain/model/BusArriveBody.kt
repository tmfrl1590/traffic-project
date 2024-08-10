package com.traffic.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//import com.google.gson.annotations.SerializedName

@Serializable
data class BusArriveBody(
    @SerialName("RESULT")
    //@SerializedName("RESULT")
    val result : Map<String, String>,

    //@SerializedName("BUSSTOP_LIST")
    @SerialName("BUSSTOP_LIST")
    val itemList : ArrayList<com.traffic.domain.model.BusArriveModel>,

    //@SerializedName("ROW_COUNT")
    @SerialName("ROW_COUNT")
    val rowCount : String
)


@Serializable
data class BusArriveModel(
    //@SerializedName("ARRIVE")
    @SerialName("ARRIVE")
    val arrive : String?,

    //@SerializedName("REMAIN_STOP")
    @SerialName("REMAIN_STOP")
    val remain_stop : String?,

    //@SerializedName("SHORT_LINE_NAME")
    @SerialName("SHORT_LINE_NAME")
    val short_line_name : String?,

    //@SerializedName("BUS_ID")
    @SerialName("BUS_ID")
    val bus_id : String?,

    //@SerializedName("METRO_FLAG")
    @SerialName("METRO_FLAG")
    val metro_flag : String?,

    //@SerializedName("BUSSTOP_NAME")
    @SerialName("BUSSTOP_NAME")
    val busstop_name : String?,

    //@SerializedName("CURR_STOP_ID")
    @SerialName("CURR_STOP_ID")
    val curr_stop_id : String?,

    //@SerializedName("LINE_ID")
    @SerialName("LINE_ID")
    val line_id : String?,

    //@SerializedName("REMAIN_MIN")
    @SerialName("REMAIN_MIN")
    val remain_min : String?,

    //@SerializedName("ENG_BUSSTOP_NAME")
    @SerialName("ENG_BUSSTOP_NAME")
    val eng_busstop_name : String?,

    //@SerializedName("DIR_START")
    @SerialName("DIR_START")
    val dir_start : String?,

    //@SerializedName("DIR")
    @SerialName("DIR")
    val dir : String?,

    //@SerializedName("DIR_END")
    @SerialName("DIR_END")
    val dir_end : String?,

    //@SerializedName("LOW_BUS")
    @SerialName("LOW_BUS")
    val low_bus : String?,

    //@SerializedName("ARRIVE_FLAG")
    @SerialName("ARRIVE_FLAG")
    val arrive_flag : String?,

    //@SerializedName("LINE_NAME")
    @SerialName("LINE_NAME")
    val line_name : String?
)