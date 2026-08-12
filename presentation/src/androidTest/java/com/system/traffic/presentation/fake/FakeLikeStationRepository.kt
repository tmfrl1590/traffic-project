package com.system.traffic.presentation.fake

import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.LikeStationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * 테스트용 LikeStationRepository fake.
 * 인메모리 리스트로 실제처럼 동작한다:
 * 추가/삭제하면 getLikeStationList() Flow가 바뀐 리스트를 방출하므로
 * "토글 → 목록 갱신" 흐름을 UI 테스트에서 그대로 검증할 수 있다.
 */
class FakeLikeStationRepository(
    initialStations: List<StationModel> = emptyList(),
) : LikeStationRepository {

    private val stations = MutableStateFlow(initialStations)

    override suspend fun addLikeStation(stationModel: StationModel) {
        stations.update { it + stationModel }
    }

    override suspend fun deleteLikeStation(arsId: String) {
        stations.update { list -> list.filterNot { it.arsId == arsId } }
    }

    override fun getLikeStationList(): Flow<List<StationModel>> = stations
}
