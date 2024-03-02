package com.system.traffic.data.remote

import com.system.traffic.domain.model.BusArriveBody
import com.system.traffic.domain.model.LineStationBody
import retrofit2.http.GET
import retrofit2.http.Query

interface TrafficApi {

    // 버스 도착정보
    @GET("arriveInfo")
    suspend fun getBusArriveList(@Query("serviceKey") serviceKey: String, @Query("BUSSTOP_ID") busStopId : String) : BusArriveBody

    // 버스 경유지 정보
    @GET("lineStationInfo")
    suspend fun lineStationInfo(@Query("serviceKey") serviceKey: String, @Query("LINE_ID") lineId : String ) : LineStationBody
}