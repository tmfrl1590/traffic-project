package com.traffic.data.remote

import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import com.traffic.data.model.remote.BusArriveEntity

interface RemoteDataSource {

    suspend fun getBusArriveList(arsId : String): Result<BusArriveEntity, DataError.Remote>
}