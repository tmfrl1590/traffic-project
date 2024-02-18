package com.system.traffic.domain.useCase

import com.system.traffic.data.repository.Repository
import com.system.traffic.domain.dataModel.StationEntity
import com.system.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StationUseCase (
    //private val repository: Repository,
    private val stationRepository: StationRepository
){

    fun getLikeStationList(): Flow<List<StationEntity>>{
        return stationRepository.getLikeStationList()
    }

    fun getSearchedStationList(keyword: String): List<StationEntity>{
        return stationRepository.getSearchedStationList(keyword)
    }

    fun updateStation(stationEntity: StationEntity){
        stationRepository.updateStation(stationEntity)
    }

    fun getStationInfo(ars_id: String): Flow<StationEntity>{
        return stationRepository.getStationInfo(ars_id)
    }
}