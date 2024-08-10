package com.traffic.data.repository

import com.skydoves.sandwich.ApiResponse
import com.traffic.common.Constants
import com.traffic.data.remote.TrafficService
import com.traffic.domain.model.BusArriveModel
import com.traffic.domain.repository.BusArriveRepository
import javax.inject.Inject

class BusArriveRepositoryImpl @Inject constructor(
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