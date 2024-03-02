package com.system.traffic.data.repository

import com.system.traffic.data.local.db.dao.StationDao
import com.system.traffic.domain.model.StationModel
import com.system.traffic.data.local.db.entity.toLikeStationModel
import com.system.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StationRepositoryImpl(
    private val stationDao: StationDao,
) : StationRepository {

    // 정류장 검색
    override fun getSearchedStationList(keyword: String): List<StationModel> {
        return stationDao.getSearchedStationList(keyword).map { it.toLikeStationModel() }
    }


    override fun getStationInfo(arsId: String): Flow<StationModel> {
        return stationDao.getStationInfo(arsId).map { it.toLikeStationModel() }
    }
}

