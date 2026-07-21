package com.traffic.domain.usecase.pinned_bus

import com.traffic.domain.repository.PinnedBusRepository
import javax.inject.Inject

class GetPinnedBusUseCase @Inject constructor(
    private val pinnedBusRepository: PinnedBusRepository
) {
    operator fun invoke(busStopId: String) = pinnedBusRepository.getPinnedBusList(busStopId = busStopId)
}