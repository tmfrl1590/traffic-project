package com.traffic.domain.usecase.arrive

import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import com.system.traffic.core.domain.map
import com.traffic.domain.model.BusArrive
import com.traffic.domain.repository.RemoteRepository
import com.traffic.domain.repository.StationRepository
import javax.inject.Inject

class BusArriveUseCase @Inject constructor(
    private val busArriveRepository: RemoteRepository,
    private val stationRepository: StationRepository,
) {
    suspend operator fun invoke(busStopId: String): Result<BusArrive, DataError.Remote> {
        return busArriveRepository.getBusArriveList(busStopId = busStopId).map { busArrive ->
            // 1. 도착 예정인 버스들의 현재 정류장 ID 목록 추출 (중복 제거)
            val stopIds = busArrive.itemList.mapNotNull { it.currStopId }.distinct()

            // 2. [얼리 리턴] 정류장 목록이 비어있으면 즉시 원래 데이터를 반환하고 종료
            if (stopIds.isEmpty()) return@map busArrive

            // 3. 로컬 DB 조회 및 검색용 Map 생성을 한 번에 연결(Chaining)
            val coordinateMap = stationRepository.getLocationInfo(stopIds).associateBy { it.busStopId }

            // 4. 각 버스 아이템에 위도/경도 매핑
            val updatedItems = busArrive.itemList.map { item ->
                val coord = coordinateMap[item.currStopId]
                item.copy(
                    busLatitude = coord?.latitude?.toDoubleOrNull(),
                    busLongitude = coord?.longitude?.toDoubleOrNull()
                )
            }

            // 5. 최종 가공된 버스 리스트로 복사본 반환
            busArrive.copy(itemList = updatedItems)
        }
    }
}