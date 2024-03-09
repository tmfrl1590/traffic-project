package com.system.traffic.domain.repository

import com.system.traffic.common.Resource
import com.system.traffic.domain.model.BusArriveBody
import kotlinx.coroutines.flow.Flow

interface BusArriveRepository {

    suspend fun getBusArriveList(arsId : String): Resource<BusArriveBody>
}