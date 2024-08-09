package com.system.traffic.domain.repository

import com.system.traffic.common.UIState
import com.system.traffic.domain.model.BusArriveBody
import com.system.traffic.domain.model.BusArriveModel

interface BusArriveRepository {

    suspend fun getBusArriveList(arsId : String): ArrayList<BusArriveModel>
}