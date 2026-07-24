package com.traffic.domain.usecase.pinned_bus

import com.traffic.domain.repository.PinnedBusRepository
import javax.inject.Inject

class ResetPinnedBusUseCase @Inject constructor(
    private val pinnedBusRepository: PinnedBusRepository
) {
    suspend operator fun invoke() = pinnedBusRepository.resetPinnedBus()
}