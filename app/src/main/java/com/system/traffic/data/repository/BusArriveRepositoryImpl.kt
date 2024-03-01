package com.system.traffic.data.repository

import com.system.traffic.domain.dataModel.BusArriveBody
import com.system.traffic.domain.repository.BusArriveRepository
import com.system.traffic.common.Constants
import com.system.traffic.common.Resource
import com.system.traffic.data.remote.TrafficApi

class BusArriveRepositoryImpl (
    private val trafficApi: TrafficApi,
): BusArriveRepository {
    override suspend fun getBusArriveList(arsId: String): Resource<BusArriveBody>  {
        val response = try {
            Resource.Loading(data = true)
            trafficApi.getBusArriveList(Constants.SERVICE_KEY, arsId)
        }catch (exception: Exception){
            return Resource.Error(message = "An error occurred ${exception.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}