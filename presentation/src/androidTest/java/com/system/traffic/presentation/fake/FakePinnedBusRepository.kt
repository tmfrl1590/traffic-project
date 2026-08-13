package com.system.traffic.presentation.fake

import com.system.traffic.domain.model.PinnedBusModel
import com.system.traffic.domain.repository.PinnedBusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/**
 * 테스트용 PinnedBusRepository fake.
 * 인메모리 리스트로 실제처럼 동작한다.
 */
class FakePinnedBusRepository(
    initialPinnedBuses: List<PinnedBusModel> = emptyList(),
) : PinnedBusRepository {

    private val pinnedBuses = MutableStateFlow(initialPinnedBuses)

    override suspend fun insertPinnedBus(busStopId: String, lineId: String) {
        pinnedBuses.update {
            it + PinnedBusModel(busStopId = busStopId, lineId = lineId)
        }
    }

    override suspend fun deletePinnedBus(busStopId: String, lineId: String) {
        pinnedBuses.update { list ->
            list.filterNot { it.busStopId == busStopId && it.lineId == lineId }
        }
    }

    override fun getPinnedBusList(busStopId: String): Flow<List<PinnedBusModel>> =
        pinnedBuses.map { list -> list.filter { it.busStopId == busStopId } }

    override suspend fun resetPinnedBus() {
        pinnedBuses.update { emptyList() }
    }
}
