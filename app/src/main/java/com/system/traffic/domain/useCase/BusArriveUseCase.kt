package com.system.traffic.domain.useCase

import com.system.traffic.domain.model.BusArriveModel
import com.system.traffic.domain.repository.BusArriveRepository

class BusArriveUseCase (
    private val repository: BusArriveRepository
) {
    suspend fun getBusArriveList(arsId: String): ArrayList<BusArriveModel> {
        return repository.getBusArriveList(arsId)
    }
}