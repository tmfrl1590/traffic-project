package com.traffic.data.impl

import com.traffic.data.local.LocalDataSource
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : StationRepository {
    override fun getSearchedStationList(keyword: String): Flow<List<StationModel>> = flow {
        try {
            val searchedStationList = localDataSource.getSearchedStationList(keyword).map { it.toDomain() }
            emit(value = searchedStationList)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getStationInfo(arsId: String): Flow<StationModel> = flow {
        try {
            val stationInfo = localDataSource.getStationInfo(arsId = arsId).toDomain()
            emit(stationInfo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

