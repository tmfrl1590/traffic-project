package com.system.traffic.data.impl

import com.system.traffic.data.local.LocalDataSource
import com.system.traffic.data.model.local.toEntity
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.LikeStationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikeStationRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
): LikeStationRepository {
    override suspend fun addLikeStation(stationModel: StationModel) {
        localDataSource.addLikeStation(stationModel.toEntity())
    }

    override suspend fun deleteLikeStation(arsId: String) {
        localDataSource.deleteLikeStation(arsId = arsId)
    }

    override fun getLikeStationList(): Flow<List<StationModel>> {
        return localDataSource.getLikeStationList().map { list ->
            list.map { it.toDomain() }
        }
    }
}