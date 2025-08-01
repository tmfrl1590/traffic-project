package com.system.traffic.remote.impl

import com.system.traffic.core.data.safeCall
import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import com.system.traffic.remote.service.TrafficService
import com.traffic.data.model.remote.BusArriveEntity
import com.traffic.data.remote.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val trafficService: TrafficService,
): RemoteDataSource{
    override suspend fun getBusArriveList(arsId: String): Result<BusArriveEntity, DataError.Remote> {
        return safeCall<BusArriveEntity> {
            trafficService.getBusArriveList(busStopId = arsId)
        }
    }

}