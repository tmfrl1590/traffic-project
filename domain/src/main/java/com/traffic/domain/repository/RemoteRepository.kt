package com.traffic.domain.repository

import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import com.traffic.domain.model.BusArrive

interface RemoteRepository {

    suspend fun getBusArriveList(arsId : String): Result<BusArrive, DataError.Remote>
}