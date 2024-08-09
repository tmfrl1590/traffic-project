package com.system.traffic.domain.useCase.like

import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.LikeStationRepository
import javax.inject.Inject

class AddLikeStationUseCase @Inject constructor(
    private val likeStationRepository: LikeStationRepository,
) {
    suspend operator fun invoke(stationModel: StationModel){
        likeStationRepository.addLikeStation(stationModel = stationModel)
    }
}