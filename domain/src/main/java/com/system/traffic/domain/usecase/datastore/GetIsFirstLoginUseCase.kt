package com.system.traffic.domain.usecase.datastore

import com.system.traffic.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetIsFirstLoginUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(): Boolean{
        return dataStoreRepository.getIsFirstLogin()
    }
}