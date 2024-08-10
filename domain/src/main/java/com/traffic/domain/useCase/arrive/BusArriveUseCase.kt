package com.traffic.domain.useCase.arrive

import com.traffic.domain.model.BusArriveModel
import com.traffic.domain.repository.BusArriveRepository
import javax.inject.Inject

class BusArriveUseCase @Inject constructor(
    private val repository: BusArriveRepository
) {
    suspend fun getBusArriveList(arsId: String): ArrayList<BusArriveModel> {
        return repository.getBusArriveList(arsId)
    }
}