package com.traffic.domain.useCase.arrive

import com.traffic.common.BaseResponse
import com.traffic.domain.model.BusArriveItem
import com.traffic.domain.repository.BusArriveRepository
import javax.inject.Inject

class BusArriveUseCase @Inject constructor(
    private val busArriveRepository: BusArriveRepository
) {
    suspend operator fun invoke(arsId: String): BaseResponse<List<BusArriveItem>> {
        return busArriveRepository.getBusArriveList(arsId)
    }
}