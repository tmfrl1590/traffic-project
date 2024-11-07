package com.traffic.data.repository

import com.skydoves.sandwich.ApiResponse
import com.traffic.common.BaseResponse
import com.traffic.common.Constants
import com.traffic.common.ResultCode
import com.traffic.data.remote.mapper.BusArriveMapper.mapperToBusArriveItem
import com.traffic.data.remote.service.TrafficService
import com.traffic.domain.model.BusArriveItem
import com.traffic.domain.repository.BusArriveRepository
import javax.inject.Inject

class BusArriveRepositoryImpl @Inject constructor(
    private val trafficApi: TrafficService,
): BusArriveRepository {
    override suspend fun getBusArriveList(arsId: String): BaseResponse<List<BusArriveItem>> {
        return when(val result = trafficApi.getBusArriveList(Constants.SERVICE_KEY, arsId)){
            is ApiResponse.Success -> {
                BaseResponse(
                    code = ResultCode.BUS_ARRIVE_GET_SUCCESS.code,
                    message = ResultCode.BUS_ARRIVE_GET_SUCCESS.message,
                    data = mapperToBusArriveItem(result.data.itemList)
                )
            }
            is ApiResponse.Failure.Error-> {
                BaseResponse(
                    code = ResultCode.BUS_ARRIVE_GET_FAILURE.code,
                    message = ResultCode.BUS_ARRIVE_GET_FAILURE.message,
                )
            }
            is ApiResponse.Failure.Exception -> {
                BaseResponse(
                    code = ResultCode.COMMON_EXCEPTION.code,
                    message = ResultCode.COMMON_EXCEPTION.message,
                )
            }
        }
    }
}