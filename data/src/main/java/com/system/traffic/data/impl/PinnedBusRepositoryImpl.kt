package com.system.traffic.data.impl

import com.system.traffic.data.local.LocalDataSource
import com.system.traffic.data.model.local.toDomain
import com.system.traffic.domain.model.PinnedBusModel
import com.system.traffic.domain.repository.PinnedBusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PinnedBusRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): PinnedBusRepository {
    override suspend fun insertPinnedBus(busStopId: String, lineId: String) {
        localDataSource.addPinnedBus(busStopId = busStopId, lineId = lineId)
    }

    override suspend fun deletePinnedBus(busStopId: String, lineId: String) {
        localDataSource.deletePinnedBus(busStopId = busStopId, lineId = lineId)
    }

    override fun getPinnedBusList(busStopId: String): Flow<List<PinnedBusModel>> {
        return localDataSource.getPinnedBusList(busStopId = busStopId).map { it.map { it.toDomain() } }
    }

    override suspend fun resetPinnedBus() {
        localDataSource.resetPinnedBus()
    }
}