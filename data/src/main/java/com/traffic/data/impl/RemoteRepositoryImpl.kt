package com.traffic.data.impl

import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import com.system.traffic.core.domain.map
import com.traffic.data.remote.RemoteDataSource
import com.traffic.domain.model.BusArrive
import com.traffic.domain.model.LineStation
import com.traffic.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
): RemoteRepository {
    override suspend fun getBusArriveList(busStopId: String): Result<BusArrive, DataError.Remote> {
        return remoteDataSource.getBusArriveList(busStopId = busStopId).map { it.toDomain() }
    }

    override suspend fun getLineStationList(lineId: String): Result<LineStation, DataError.Remote> {
        return remoteDataSource.getLineStationList(lineId = lineId).map { it.toDomain() }
    }
}