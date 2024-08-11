package com.traffic.data.service

import com.skydoves.sandwich.ApiResponse
import com.traffic.domain.model.BusArriveBody
import com.traffic.domain.model.LineStationBody
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface TrafficService {

    // 버스 도착정보
    @GET("arriveInfo")
    suspend fun getBusArriveList(@Query("serviceKey") serviceKey: String, @Query("BUSSTOP_ID") busStopId : String) : ApiResponse<BusArriveBody>

    // 버스 경유지 정보
    @GET("lineStationInfo")
    suspend fun lineStationInfo(@Query("serviceKey") serviceKey: String, @Query("LINE_ID") lineId : String ) : LineStationBody
}