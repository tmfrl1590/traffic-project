package com.traffic.data.impl

import com.traffic.data.local.LocalDataSource
import com.traffic.domain.model.StationCoordinateModel
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : StationRepository {
    override fun getSearchedStationList(keyword: String): Flow<List<StationModel>> = flow {
        emit(localDataSource.getSearchedStationList(keyword).map { it.toDomain() })
    }

    override fun getStationInfo(arsId: String): Flow<StationModel> = flow {
        emit(localDataSource.getStationInfo(arsId = arsId).toDomain())
    }

    override suspend fun getLocationInfo(ids: List<String?>): List<StationCoordinateModel> {
        return localDataSource.getLocationInfo(ids = ids).map { it.toDomain() }
    }
}

