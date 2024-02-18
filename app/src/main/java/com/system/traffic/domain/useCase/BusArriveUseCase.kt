package com.system.traffic.domain.useCase

import com.system.traffic.data.repository.Repository
import com.system.traffic.domain.repository.BusArriveRepository
import javax.inject.Inject

class BusArriveUseCase (
    //private val repository: Repository
    private val repository: BusArriveRepository
) {
    suspend fun getBusArriveList(ars_id: String) = repository.getBusArriveList(ars_id)
}