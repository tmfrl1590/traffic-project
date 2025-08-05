package com.traffic.domain.usecase.station

import com.traffic.common.Resource
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStationInfoUseCase @Inject constructor(
    private val stationRepository: StationRepository
){
    operator fun invoke(arsId: String): Flow<Resource<StationModel>> {
        return stationRepository.getStationInfo(arsId)
    }
}