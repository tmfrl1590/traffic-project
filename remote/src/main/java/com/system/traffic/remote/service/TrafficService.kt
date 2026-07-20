package com.system.traffic.remote.service

import com.system.traffic.remote.RemoteConstants
import com.traffic.data.model.remote.BusArriveEntity
import com.traffic.data.model.remote.LineStationInfoEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrafficService {

    // 버스 도착정보
    @GET(value = "arriveInfo")
    suspend fun getBusArriveList(
        @Query("serviceKey", encoded = true) serviceKey: String = RemoteConstants.SERVICE_KEY,
        @Query("BUSSTOP_ID") busStopId : String,
    ): Response<BusArriveEntity>

    // 노선 경유지 정보
    @GET(value = "lineStationInfo")
    suspend fun getLineStationInfo(
        @Query("serviceKey", encoded = true) serviceKey: String = RemoteConstants.SERVICE_KEY,
        @Query(value = "LINE_ID") lineId : String,
        @Query(value = "resultType") resultType : String = "json",
    ): Response<LineStationInfoEntity>
}