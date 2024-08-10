package com.traffic.domain.useCase.like

import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.LikeStationRepository
import javax.inject.Inject

class AddLikeStationUseCase @Inject constructor(
    private val likeStationRepository: com.traffic.domain.repository.LikeStationRepository,
) {
    suspend operator fun invoke(stationModel: com.traffic.domain.model.StationModel){
        likeStationRepository.addLikeStation(stationModel = stationModel)
    }
}