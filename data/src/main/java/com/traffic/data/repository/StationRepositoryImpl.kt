package com.traffic.data.repository

import com.traffic.data.local.db.dao.StationDao
import com.traffic.data.local.db.entity.toLikeStationModel
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val stationDao: StationDao,
) : StationRepository {

    // 정류장 검색
    override fun getSearchedStationList(keyword: String): Flow<List<StationModel>> {
        return stationDao.getSearchedStationList(keyword).map { list ->
            list.map { it.toLikeStationModel() }
        }
    }

    override fun getStationInfo(arsId: String): Flow<StationModel> {
        return stationDao.getStationInfo(arsId).map {
            it.toLikeStationModel()
        }
    }
}

