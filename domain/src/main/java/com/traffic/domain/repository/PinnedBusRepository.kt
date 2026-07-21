package com.traffic.domain.repository

import com.traffic.domain.model.PinnedBusModel
import kotlinx.coroutines.flow.Flow

interface PinnedBusRepository {

    suspend fun insertPinnedBus(busStopId: String, lineId: String)

    suspend fun deletePinnedBus(busStopId: String, lineId: String)

    fun getPinnedBusList(busStopId: String): Flow<List<PinnedBusModel>>
}