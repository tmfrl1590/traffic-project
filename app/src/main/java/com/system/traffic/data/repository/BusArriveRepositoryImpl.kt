package com.system.traffic.data.repository

import com.system.traffic.domain.dataModel.BusArriveBody
import com.system.traffic.domain.repository.BusArriveRepository
import com.system.traffic.common.Constants
import com.system.traffic.data.remote.TrafficApi

class BusArriveRepositoryImpl (
    private val trafficApi: TrafficApi,
): BusArriveRepository {
    override suspend fun getBusArriveList(arsId: String): BusArriveBody {
        return trafficApi.getBusArriveList(Constants.SERVICE_KEY, arsId)
    }
}