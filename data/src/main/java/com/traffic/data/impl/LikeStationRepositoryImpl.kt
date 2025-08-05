package com.traffic.data.impl

import com.traffic.data.local.LocalDataSource
import com.traffic.data.model.local.toEntity
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.LikeStationRepository
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