package com.system.traffic.domain.repository

import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import com.system.traffic.domain.model.BusArrive
import com.system.traffic.domain.model.LineStation

interface RemoteRepository {

    suspend fun getBusArriveList(busStopId : String): Result<BusArrive, DataError.Remote>

    suspend fun getLineStationList(lineId: String): Result<LineStation, DataError.Remote>
}