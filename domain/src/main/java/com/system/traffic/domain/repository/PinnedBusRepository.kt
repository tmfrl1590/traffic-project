package com.system.traffic.domain.repository

import com.system.traffic.domain.model.PinnedBusModel
import kotlinx.coroutines.flow.Flow

interface PinnedBusRepository {

    suspend fun insertPinnedBus(busStopId: String, lineId: String)

    suspend fun deletePinnedBus(busStopId: String, lineId: String)

    fun getPinnedBusList(busStopId: String): Flow<List<PinnedBusModel>>

    suspend fun resetPinnedBus()
}