package com.traffic.data.impl

import com.traffic.common.Resource
import com.traffic.data.local.LocalDataSource
import com.traffic.domain.model.StationModel
import kotlinx.coroutines.channels.awaitClose
import com.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : StationRepository {

    // 정류장 검색
    override fun getSearchedStationList(keyword: String): Flow<Resource<List<StationModel>>> = callbackFlow {
        try {
            trySend(Resource.Loading())
            val searchedStationList =
                localDataSource.getSearchedStationList(keyword).map { it.toDomain() }
            trySend(Resource.Success(searchedStationList))
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(Resource.Error())
        }
        awaitClose {}
    }

    override fun getStationInfo(arsId: String): Flow<Resource<StationModel>> {
        return localDataSource.getStationInfo(arsId = arsId).map { stationEntity ->
            try {
                Resource.Success(stationEntity.toDomain())
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error()
            }
        }
    }
}

