package com.traffic.domain.usecase.station

import com.traffic.common.Resource
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchStationUseCase @Inject constructor(
    private val stationRepository: StationRepository,
) {
    operator fun invoke(keyword: String): Flow<Resource<List<StationModel>>>{
        return stationRepository.getSearchedStationList(keyword = keyword)
    }
}