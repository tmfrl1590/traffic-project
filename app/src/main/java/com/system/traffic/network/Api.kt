package com.system.traffic.network

import com.system.traffic.dataModel.BusArriveBody
import com.system.traffic.dataModel.BusArriveModel
import com.system.traffic.dataModel.LineStationBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    // 버스 도착정보
    @GET("arriveInfo")
    suspend fun getBusArriveList(@Query("serviceKey") serviceKey: String, @Query("BUSSTOP_ID") busstop_id : String?) : BusArriveBody

    // 버스 경유지 정보
    @GET("lineStationInfo")
    suspend fun lineStationInfo(@Query("serviceKey") serviceKey: String, @Query("LINE_ID") line_id : String ) : LineStationBody
}