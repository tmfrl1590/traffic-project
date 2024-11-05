package com.traffic.data.repository

import com.traffic.common.Resource
import com.traffic.data.local.db.dao.StationDao
import com.traffic.data.local.db.entity.toLikeStationModel
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.StationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val stationDao: StationDao,
) : StationRepository {

    // 정류장 검색
    override fun getSearchedStationList(keyword: String): Flow<Resource<List<StationModel>>> = callbackFlow {
        try {
            trySend(Resource.Loading())
            delay(1000)
            val searchedStationList = stationDao.getSearchedStationList(keyword).map {
                it.toLikeStationModel()
            }
            trySend(Resource.Success(searchedStationList))
        }catch (e: Exception) {
            e.printStackTrace()
            trySend(Resource.Error())
        }
        awaitClose()
    }

    override fun getStationInfo(arsId: String): Flow<StationModel> {
        return stationDao.getStationInfo(arsId).map {
            it.toLikeStationModel()
        }
    }
}

