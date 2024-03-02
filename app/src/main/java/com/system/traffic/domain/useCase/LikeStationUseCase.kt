package com.system.traffic.domain.useCase

import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.LikeStationRepository
import kotlinx.coroutines.flow.Flow

class LikeStationUseCase(
    private val likeStationRepository: LikeStationRepository
) {

    suspend fun addLikeStation(stationModel: StationModel){
        likeStationRepository.addLikeStation(stationModel)
    }

    suspend fun deleteLikeStation(arsId: String){
        likeStationRepository.deleteLikeStation(arsId)
    }

    fun getLikeStationList(): Flow<List<StationModel>>{
        return likeStationRepository.getLikeStationList()
    }
}