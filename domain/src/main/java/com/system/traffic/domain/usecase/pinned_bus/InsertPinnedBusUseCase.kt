package com.system.traffic.domain.usecase.pinned_bus

import com.system.traffic.domain.repository.PinnedBusRepository
import javax.inject.Inject

class InsertPinnedBusUseCase @Inject constructor(
    private val pinnedBusRepository: PinnedBusRepository
) {
    suspend operator fun invoke(busStopId: String, lineId: String) {
        pinnedBusRepository.insertPinnedBus(busStopId, lineId)
    }
}