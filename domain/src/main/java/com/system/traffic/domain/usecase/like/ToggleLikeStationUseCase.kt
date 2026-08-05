package com.system.traffic.domain.usecase.like

import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.LikeStationRepository
import javax.inject.Inject

class ToggleLikeStationUseCase @Inject constructor(
    private val likeStationRepository: LikeStationRepository,
) {
    suspend operator fun invoke(stationModel: StationModel) {
        if (stationModel.selected) {
            likeStationRepository.deleteLikeStation(stationModel.arsId ?: "")
        } else {
            likeStationRepository.addLikeStation(stationModel)
        }
    }
}