package com.system.traffic.domain.usecase.pinned_bus

import com.system.traffic.domain.repository.PinnedBusRepository
import javax.inject.Inject

class GetPinnedBusUseCase @Inject constructor(
    private val pinnedBusRepository: PinnedBusRepository
) {
    operator fun invoke(busStopId: String) = pinnedBusRepository.getPinnedBusList(busStopId = busStopId)
}