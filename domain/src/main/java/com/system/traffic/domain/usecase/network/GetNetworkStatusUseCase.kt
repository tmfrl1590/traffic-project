package com.system.traffic.domain.usecase.network

import com.system.traffic.domain.repository.NetworkRepository
import javax.inject.Inject

class GetNetworkStatusUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke() = networkRepository.isNetworkConnected
}