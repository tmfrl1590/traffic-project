package com.traffic.domain.repository

import com.traffic.common.BaseResponse
import com.traffic.domain.model.BusArriveItem

interface BusArriveRepository {

    suspend fun getBusArriveList(arsId : String): BaseResponse<List<BusArriveItem>>
}