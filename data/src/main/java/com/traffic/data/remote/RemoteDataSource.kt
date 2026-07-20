package com.traffic.data.remote

import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import com.traffic.data.model.remote.BusArriveEntity
import com.traffic.data.model.remote.LineStationInfoEntity

interface RemoteDataSource {

    suspend fun getBusArriveList(arsId : String): Result<BusArriveEntity, DataError.Remote>

    suspend fun getLineStationList(lineId: String): Result<LineStationInfoEntity, DataError.Remote>
}