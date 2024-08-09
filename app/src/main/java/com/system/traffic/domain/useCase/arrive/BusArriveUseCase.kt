package com.system.traffic.domain.useCase.arrive

import com.system.traffic.domain.model.BusArriveModel
import com.system.traffic.domain.repository.BusArriveRepository
import javax.inject.Inject

class BusArriveUseCase @Inject constructor(
    private val repository: BusArriveRepository
) {
    suspend fun getBusArriveList(arsId: String): ArrayList<BusArriveModel> {
        return repository.getBusArriveList(arsId)
    }
}