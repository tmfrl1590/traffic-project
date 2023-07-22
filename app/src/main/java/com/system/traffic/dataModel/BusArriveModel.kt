package com.system.traffic.dataModel

import com.google.gson.annotations.SerializedName

data class BusArriveBody(
    @SerializedName("RESULT")
    val result : Map<String, String>,

    @SerializedName("BUSSTOP_LIST")
    val itemList : ArrayList<BusArriveModel>,

    @SerializedName("ROW_COUNT")
    val row_count : String
)
data class BusArriveModel(
    @SerializedName("ARRIVE")
    val arrive : String?,

    @SerializedName("REMAIN_STOP")
    val remain_stop : String?,

    @SerializedName("SHORT_LINE_NAME")
    val short_line_name : String?,

    @SerializedName("BUS_ID")
    val bus_id : String?,

    @SerializedName("METRO_FLAG")
    val metro_flag : String?,

    @SerializedName("BUSSTOP_NAME")
    val busstop_name : String?,

    @SerializedName("CURR_STOP_ID")
    val curr_stop_id : String?,

    @SerializedName("LINE_ID")
    val line_id : String?,

    @SerializedName("REMAIN_MIN")
    val remain_min : String?,

    @SerializedName("ENG_BUSSTOP_NAME")
    val eng_busstop_name : String?,

    @SerializedName("DIR_START")
    val dir_start : String?,

    @SerializedName("DIR")
    val dir : String?,

    @SerializedName("DIR_END")
    val dir_end : String?,

    @SerializedName("LOW_BUS")
    val low_bus : String?,

    @SerializedName("ARRIVE_FLAG")
    val arrive_flag : String?,

    @SerializedName("LINE_NAME")
    val line_name : String?
)