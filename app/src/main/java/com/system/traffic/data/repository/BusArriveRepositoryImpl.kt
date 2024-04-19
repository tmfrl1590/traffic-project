package com.system.traffic.data.repository

import com.system.traffic.domain.model.BusArriveBody
import com.system.traffic.domain.repository.BusArriveRepository
import com.system.traffic.common.Constants
import com.system.traffic.common.UIState
import com.system.traffic.data.remote.TrafficService

class BusArriveRepositoryImpl (
    private val trafficApi: TrafficService,
): BusArriveRepository {
    override suspend fun getBusArriveList(arsId: String): UIState<BusArriveBody>  {
        val response = try {
            trafficApi.getBusArriveList(Constants.SERVICE_KEY, arsId)
        }catch (exception: Exception){
            return UIState.Error()
        }
        return UIState.Success(data = response)
    }
}