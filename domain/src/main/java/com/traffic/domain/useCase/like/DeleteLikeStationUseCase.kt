package com.traffic.domain.useCase.like

import com.traffic.domain.repository.LikeStationRepository
import javax.inject.Inject

class DeleteLikeStationUseCase @Inject constructor(
    private val likeStationRepository: LikeStationRepository,
){
    suspend operator fun invoke(arsId: String){
        likeStationRepository.deleteLikeStation(arsId)
    }
}