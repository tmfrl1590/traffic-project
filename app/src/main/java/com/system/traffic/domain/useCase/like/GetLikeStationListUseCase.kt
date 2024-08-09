package com.system.traffic.domain.useCase.like

import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.LikeStationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikeStationListUseCase @Inject constructor(
    private val likeStationRepository: LikeStationRepository
) {
    operator fun invoke(): Flow<List<StationModel>> {
        return likeStationRepository.getLikeStationList()
    }
}