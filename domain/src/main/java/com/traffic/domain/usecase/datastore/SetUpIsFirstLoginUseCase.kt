package com.traffic.domain.usecase.datastore

import com.traffic.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetUpIsFirstLoginUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
){
    suspend operator fun invoke(){
        dataStoreRepository.setUpIsFirstLogin()
    }
}