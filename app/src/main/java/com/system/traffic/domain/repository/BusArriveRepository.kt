package com.system.traffic.domain.repository

import com.system.traffic.domain.dataModel.BusArriveBody

interface BusArriveRepository {

    suspend fun getBusArriveList(arsId : String): BusArriveBody
}