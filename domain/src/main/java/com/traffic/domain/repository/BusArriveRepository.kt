package com.traffic.domain.repository

import com.traffic.domain.model.BusArriveModel

interface BusArriveRepository {

    suspend fun getBusArriveList(arsId : String): ArrayList<BusArriveModel>
}