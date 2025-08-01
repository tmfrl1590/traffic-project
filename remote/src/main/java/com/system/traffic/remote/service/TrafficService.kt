package com.system.traffic.remote.service

import com.system.traffic.remote.RemoteConstants
import com.traffic.data.model.remote.BusArriveEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrafficService {

    // 버스 도착정보
    @GET("arriveInfo")
    suspend fun getBusArriveList(
        @Query("serviceKey") serviceKey: String = RemoteConstants.SERVICE_KEY,
        @Query("BUSSTOP_ID") busStopId : String,
    ): Response<BusArriveEntity>

    // 버스 경유지 정보
    //@GET("lineStationInfo")
    //suspend fun lineStationInfo(@Query("serviceKey") serviceKey: String, @Query("LINE_ID") lineId : String ) : LineStationBody
}