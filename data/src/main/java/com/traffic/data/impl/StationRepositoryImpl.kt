package com.traffic.data.impl

import com.traffic.common.Resource
import com.traffic.data.local.LocalDataSource
import com.traffic.data.model.local.toModel
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
            // TODO: 실제 검색 로직 구현 필요 - 현재는 빈 리스트 반환
            val searchedStationList =
                localDataSource.getSearchedStationList(keyword).map { it.toModel() }
            trySend(Resource.Success(searchedStationList))
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(Resource.Error())
        }
        awaitClose { /* cleanup if needed */ }
    }

    override fun getStationInfo(arsId: String): Flow<Resource<StationModel>> {
        return localDataSource.getStationInfo(arsId = arsId).map { stationEntity ->
            try {
                Resource.Success(stationEntity.toModel())
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error()
            }
        }
    }
}

