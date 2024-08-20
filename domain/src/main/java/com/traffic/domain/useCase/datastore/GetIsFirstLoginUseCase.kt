package com.traffic.domain.useCase.datastore

import com.traffic.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetIsFirstLoginUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(): Boolean{
        return dataStoreRepository.getIsFirstLogin()
    }
}