package com.system.traffic.presentation.fake

import com.system.traffic.domain.model.StationCoordinateModel
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * 테스트용 StationRepository fake.
 * 생성자로 받은 정류장 목록을 검색 대상으로 사용한다.
 * 검색은 실제 쿼리(LIKE)처럼 정류장 이름 부분 일치로 동작한다.
 */
class FakeStationRepository(
    private val stations: List<StationModel> = emptyList(),
) : StationRepository {

    override fun getSearchedStationList(keyword: String): Flow<List<StationModel>> =
        flowOf(stations.filter { it.busStopName?.contains(keyword) == true })

    override fun getStationInfo(arsId: String): Flow<StationModel> = flow {
        emit(stations.first { it.arsId == arsId })
    }

    override suspend fun getLocationInfo(ids: List<String?>): List<StationCoordinateModel> =
        emptyList()
}
