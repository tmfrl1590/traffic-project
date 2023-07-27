package com.system.traffic.repository


import com.system.traffic.network.Api
import com.system.traffic.network.RetrofitInstance

class NetWorkRepository {

    private val client = RetrofitInstance.getInstance().create(Api::class.java)

    suspend fun getBusArriveList(ars_id : String?) = client.getBusArriveList(RetrofitInstance.getServiceKey(), ars_id)

    suspend fun getLineStationInfo(line_id : String) = client.lineStationInfo(RetrofitInstance.getServiceKey(), line_id)

}