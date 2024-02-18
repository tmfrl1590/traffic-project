package com.system.traffic.data.repository

import com.system.traffic.data.local.db.dao.StationDao
import com.system.traffic.domain.dataModel.StationEntity
import com.system.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StationRepositoryImpl (
    private val stationDao: StationDao,
): StationRepository {

    override fun getLikeStationList(): Flow<List<StationEntity>> {
        return stationDao.getLikeStationList()
    }

    // 정류장 검색
    override fun getSearchedStationList(keyword: String): List<StationEntity> {
        return stationDao.getSearchedStationList(keyword)
    }

    override fun updateStation(stationEntity: StationEntity) {
        stationDao.updateStation(stationEntity)
    }

    override fun getStationInfo(ars_id: String): Flow<StationEntity> {
        return stationDao.getStationInfo(ars_id)
    }
}

