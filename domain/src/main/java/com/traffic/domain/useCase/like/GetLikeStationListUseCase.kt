package com.traffic.domain.useCase.like

import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.LikeStationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikeStationListUseCase @Inject constructor(
    private val likeStationRepository: com.traffic.domain.repository.LikeStationRepository
) {
    operator fun invoke(): Flow<List<com.traffic.domain.model.StationModel>> {
        return likeStationRepository.getLikeStationList()
    }
}