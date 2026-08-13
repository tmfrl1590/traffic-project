package com.system.traffic.domain.usecase.datastore

import com.system.traffic.core.enums.AppFontSize
import com.system.traffic.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppFontSizeUseCase @Inject constructor(
    private  val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke(): Flow<AppFontSize> {
        return dataStoreRepository.getAppFontSize()
    }
}