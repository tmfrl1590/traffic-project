package com.traffic.domain.useCase.arrive

import com.traffic.domain.repository.RemoteRepository
import javax.inject.Inject

class BusArriveUseCase @Inject constructor(
    private val busArriveRepository: RemoteRepository
) {
    suspend operator fun invoke(arsId: String) = busArriveRepository.getBusArriveList(arsId = arsId)
}