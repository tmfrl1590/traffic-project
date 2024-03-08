package com.system.traffic.domain.useCase

import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow

class StationUseCase (
    private val stationRepository: StationRepository
){

    fun getSearchedStationList(keyword: String): Flow<List<StationModel>>{
        return stationRepository.getSearchedStationList(keyword)
    }

    fun getStationInfo(arsId: String): Flow<StationModel>{
        return stationRepository.getStationInfo(arsId)
    }
}