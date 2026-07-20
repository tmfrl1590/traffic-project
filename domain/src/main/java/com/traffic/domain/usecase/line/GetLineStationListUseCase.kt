package com.traffic.domain.usecase.line

import com.traffic.domain.repository.RemoteRepository
import javax.inject.Inject

class GetLineStationListUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(lineId: String) = remoteRepository.getLineStationList(lineId = lineId)
}