package com.traffic.domain.useCase.arrive

import com.traffic.common.BaseResponse
import com.traffic.domain.model.BusArriveModel
import com.traffic.domain.repository.BusArriveRepository
import javax.inject.Inject

class BusArriveUseCase @Inject constructor(
    private val busArriveRepository: BusArriveRepository
) {
    suspend operator fun invoke(arsId: String): BaseResponse<List<BusArriveModel>> {
        return busArriveRepository.getBusArriveList(arsId)
    }
}