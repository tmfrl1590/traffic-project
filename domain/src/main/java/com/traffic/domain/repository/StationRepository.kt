package com.traffic.domain.repository

import com.traffic.domain.model.StationModel
import kotlinx.coroutines.flow.Flow
import com.traffic.common.Resource

/**
 * StationRepository 는 도메인 계층의 추상화로, 데이터 접근 세부사항을 모르게 함.
 * 구체 구현체는 data 모듈에서 DataSource 기반으로 제공됨 (remote/local 등)
 */
interface StationRepository {

    fun getSearchedStationList(keyword: String): Flow<Resource<List<StationModel>>>

    fun getStationInfo(arsId: String): Flow<Resource<StationModel>>
}