package com.traffic.data.repository

import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import com.system.traffic.core.domain.map
import com.traffic.data.remote.RemoteDataSource
import com.traffic.domain.model.BusArrive
import com.traffic.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
): RemoteRepository {
    override suspend fun getBusArriveList(arsId: String): Result<BusArrive, DataError.Remote> {
        return remoteDataSource.getBusArriveList(arsId = arsId).map { it.toDomain() }
    }
}