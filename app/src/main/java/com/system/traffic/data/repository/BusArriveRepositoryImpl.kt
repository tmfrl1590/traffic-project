package com.system.traffic.data.repository

import com.skydoves.sandwich.ApiResponse
import com.system.traffic.domain.model.BusArriveBody
import com.system.traffic.domain.repository.BusArriveRepository
import com.system.traffic.common.Constants
import com.system.traffic.common.UIState
import com.system.traffic.data.remote.TrafficService
import com.system.traffic.domain.model.BusArriveModel

class BusArriveRepositoryImpl (
    private val trafficApi: TrafficService,
): BusArriveRepository {
    override suspend fun getBusArriveList(arsId: String): ArrayList<BusArriveModel> {
        return when(val result = trafficApi.getBusArriveList(Constants.SERVICE_KEY, arsId)){
            is ApiResponse.Success -> {
                result.data.itemList
            }
            is ApiResponse.Failure.Error-> {
                ArrayList()
            }
            is ApiResponse.Failure.Exception -> {
                ArrayList()
            }
        }
    }
}