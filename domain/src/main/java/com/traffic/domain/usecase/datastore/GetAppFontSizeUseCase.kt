package com.traffic.domain.usecase.datastore

import com.traffic.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppFontSizeUseCase @Inject constructor(
    private  val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke(): Flow<String> {
        return dataStoreRepository.getAppFontSize()
    }
}