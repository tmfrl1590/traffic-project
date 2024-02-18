package com.system.traffic.domain.repository

import com.system.traffic.domain.dataModel.BusArriveBody
import com.system.traffic.domain.dataModel.StationModel
import com.system.traffic.domain.dataModel.StationEntity
import kotlinx.coroutines.flow.Flow

interface StationRepository {

    // 즐겨찾기 가져오기(정류장)
    fun getLikeStationList(): Flow<List<StationEntity>>

    fun getSearchedStationList(keyword: String): List<StationEntity>

    fun updateStation(stationEntity: StationEntity)

    fun getStationInfo(ars_id: String): Flow<StationEntity>

}