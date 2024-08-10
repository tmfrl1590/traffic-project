package com.traffic.data.repository

import com.traffic.data.local.db.dao.LikeStationDao
import com.traffic.data.local.db.entity.toLikeStationEntity
import com.traffic.data.local.db.entity.toLikeStationModel
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.LikeStationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikeStationRepositoryImpl @Inject constructor(
    private val likeStationDao: LikeStationDao,
): LikeStationRepository {
    override suspend fun addLikeStation(stationModel: StationModel) {
        likeStationDao.addLikeStation(stationModel.toLikeStationEntity())
    }

    override suspend fun deleteLikeStation(arsId: String) {
        likeStationDao.deleteLikeStation(arsId)
    }

    override fun getLikeStationList(): Flow<List<StationModel>> {
        return likeStationDao.getLikeStationList().map { list ->
            list.map { it.toLikeStationModel() }
        }
    }
}